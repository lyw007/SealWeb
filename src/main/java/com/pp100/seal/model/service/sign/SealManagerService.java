package com.pp100.seal.model.service.sign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pp100.model.domain.JsonMapper;
import com.pp100.model.domain.JsonMapper.JsonException;
import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiGenerateContractParameter;
import com.pp100.seal.api.ApiParameter;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.api.ApiStatus;
import com.pp100.seal.model.dao.ContractBatchDao;
import com.pp100.seal.model.dao.ContractDao;
import com.pp100.seal.model.dao.ContractEntityDao;
import com.pp100.seal.model.dao.ContractEntityPartyDao;
import com.pp100.seal.model.domain.ContractBatchStatus;
import com.pp100.seal.model.domain.ContractStatus;
import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.domain.entity.ContractBatch;
import com.pp100.seal.model.domain.entity.ContractEntity;
import com.pp100.seal.model.domain.entity.ContractEntityParty;
import com.pp100.seal.model.domain.entity.ContractTemplate;
import com.pp100.seal.model.service.template.ParameterManagerService;
import com.pp100.seal.model.service.template.TemplateManagerService;

@Service
public class SealManagerService {
    private static Logger logger = Logger.getLogger(SealManagerService.class);
    private JsonMapper jsonMapper = new JsonMapper();
 
    private ContractDao contractDao;
    private ContractBatchDao contractBatchDao;
    private ContractEntityPartyDao contractEntityPartyDao;
    private ESealAccountManagerService eSealAccountManagerService;
    private ParameterManagerService parameterManagerService;
    
    @Autowired
    public SealManagerService(ContractDao contractDao, ContractBatchDao contractBatchDao,
            ContractEntityPartyDao contractEntityPartyDao, ESealAccountManagerService eSealAccountManagerService,
            ParameterManagerService parameterManagerService) {
        this.contractDao = contractDao;
        this.contractBatchDao = contractBatchDao;
        this.contractEntityPartyDao = contractEntityPartyDao;
        this.eSealAccountManagerService = eSealAccountManagerService;
        this.parameterManagerService = parameterManagerService;
    }

    /**
     * 执行电子签章
     * 
     * @return
     */
    public Map<String, Object> executeSeal(ContractTemplate contractTemplate, String parameters){
        Map<String, Object> contractESealUrl = new HashMap();
        ContractBatch contractBatch = null;
        try {
            //(1)添加到批次中
            contractBatch = contractBatchDao.create(new ContractBatch(contractTemplate, parameters, ContractBatchStatus.WAIT));
 
            //参数转对象(一个API，对应一份合同)
            List<ApiParameter> apiParameters = jsonMapper.fromJson(parameters, new TypeReference<List<ApiParameter>>() {});
            
            //遍历添加合同信息 
            for(ApiParameter apiParameter : apiParameters){
                Contract contract = getContractBySoleCode(apiParameter);
                if(contract == null){
                    contract = contractDao.create(new Contract(contractBatch, null, jsonMapper.toJson(apiParameter.getApiGenerateContractParameters()), null, ContractStatus.WAIT, apiParameter.getIdNumberOrEnterpriserCode(), apiParameter.getSoleCode()));
                }
                this.generateContract(contract ,apiParameter, null,ApiStatus.WAIT.ordinal()+"");
                
                if (null != apiParameter.getIdNumberOrEnterpriserCode()) {
                    contractESealUrl.put(apiParameter.getIdNumberOrEnterpriserCode(), contract.getId());
                }
            }
            contractBatchDao.udpateStatus(contractBatch.getId(), ContractBatchStatus.SUCCESS);
        } catch (Exception e) {
            logger.error("SealManagerService + executeSeal 执行电子签章计划失败" + parameters + "\t" + e.getMessage(), e);
            contractBatchDao.udpateStatus(contractBatch.getId(), ContractBatchStatus.FAIL);
        }
        return contractESealUrl;
    }
    
    public Contract getContractBySoleCode(ApiParameter apiParameter) {
        String soleCode = apiParameter.getSoleCode();
        if (StringUtils.isBlank(soleCode)) {
            return null;
        }
        return contractDao.getContractBySoleCode(soleCode);
    }
    
    /**
     * 生成合同(这里调用的是多线程处理机制)
     * @param contractBatchId
     * @param sealEntityId
     * @param parameters
     * @param fileId
     * @param status
     * @return
     * @throws Exception
     */
    private void generateContract(Contract contract, ApiParameter apiParameter, String fileId, String status) throws Exception {
        try{
            ApiGenerateContractParameter apiGenerateContractParameters = apiParameter.getApiGenerateContractParameters();
            
            ContractEntity contractEntity = null; 
            
            //维护合同与个人之间的关系
            for(ApiPerson apiPerson : apiGenerateContractParameters.getApiPersons()){
                ContractEntity tmpContractEntity = eSealAccountManagerService.getPersonId(apiPerson);
                if(apiPerson.getIdNumber().equals(apiParameter.getIdNumberOrEnterpriserCode())){
                    contractEntity = tmpContractEntity;
                }
                contractEntityPartyDao.create(new ContractEntityParty(tmpContractEntity, contract, apiPerson.getKeyword()));
            }
            
            //维护合同与企业之间的关系
            List<ApiEnterprise> apiEnterprises = apiGenerateContractParameters.getApiEnterprises();
            
            //把壹佰金融平台信息添加进来
            apiEnterprises = parameterManagerService.getAllApiEnterprises(apiEnterprises);
            for(int i = 0 ;apiEnterprises != null && i < apiEnterprises.size() ; i++){
                ApiEnterprise apiEnterprise = apiEnterprises.get(i);
                ContractEntity tmpContractEntity = eSealAccountManagerService.getEnterpriseId(apiEnterprise);
                if(apiEnterprise.getEnterpriseCode().equals(apiParameter.getIdNumberOrEnterpriserCode())){
                    contractEntity = tmpContractEntity;
                }
                contractEntityPartyDao.create(new ContractEntityParty(tmpContractEntity, contract, apiEnterprise.getKeyword()));
            }
            //保存合同与用户的一一对应关系
            contractDao.udpateContractEntityParty(contract.getId(), contractEntity);
        }catch(Exception e){
            logger.error(e.getMessage());
            contractDao.udpateStatus(contract.getId(), ContractStatus.FAIL, fileId, e.getMessage());
        }
    }
}
