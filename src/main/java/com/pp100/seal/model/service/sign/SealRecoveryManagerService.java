package com.pp100.seal.model.service.sign;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pp100.model.domain.JsonMapper;
import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiGenerateContractParameter;
import com.pp100.seal.api.ApiParameter;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.api.ApiStatus;
import com.pp100.seal.model.dao.ContractBatchDao;
import com.pp100.seal.model.dao.ContractDao;
import com.pp100.seal.model.dao.ContractEntityPartyDao;
import com.pp100.seal.model.domain.ContractBatchStatus;
import com.pp100.seal.model.domain.ContractStatus;
import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.domain.entity.ContractBatch;
import com.pp100.seal.model.domain.entity.ContractEntity;
import com.pp100.seal.model.domain.entity.ContractEntityParty;
import com.pp100.seal.model.service.template.ParameterManagerService;

/**
 * 用于恢复电子签章数据（使用率低）
 * 主要解决：E签宝存在用户已存在的异常
 * @author Administrator
 */
@Service
public class SealRecoveryManagerService {
    private static Logger logger = Logger.getLogger(SealRecoveryManagerService.class);
    private JsonMapper jsonMapper = new JsonMapper();
 
    private ContractDao contractDao;
    private ContractBatchDao contractBatchDao;
    private ContractEntityPartyDao contractEntityPartyDao;
    private ESealAccountManagerService eSealAccountManagerService;
    private ParameterManagerService parameterManagerService;
    
    @Autowired
    public SealRecoveryManagerService(ContractDao contractDao, ContractBatchDao contractBatchDao,
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
    public void executeSeal(ContractBatch contractBatch) {
        try {
            // 参数转对象(一个API，对应一份合同)
            List<ApiParameter> apiParameters = jsonMapper.fromJson(contractBatch.getParameters(), new TypeReference<List<ApiParameter>>() {});

            // 遍历添加合同信息
            for (ApiParameter apiParameter : apiParameters) {
                Contract contract = contractDao.getContract(contractBatch.getId(), apiParameter.getIdNumberOrEnterpriserCode());
                this.generateContract(contract, apiParameter, null, ApiStatus.WAIT.ordinal() + "");
            }
            contractBatchDao.udpateStatus(contractBatch.getId(), ContractBatchStatus.SUCCESS);
        } catch (Exception e) {
            logger.error("电子签章批次恢复失败：" + e.getMessage(), e);
            contractBatchDao.udpateStatus(contractBatch.getId(), ContractBatchStatus.FAIL);
        }
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
    private void generateContract(Contract contract, ApiParameter apiParameter, String fileId, String status)
            throws Exception {
        try {
            //清空管理数据
            deleteContractEntityParty(contract.getId());
            
            ApiGenerateContractParameter apiGenerateContractParameters = apiParameter.getApiGenerateContractParameters();
            ContractEntity contractEntity = null;

            // 维护合同与个人之间的关系
            for (ApiPerson apiPerson : apiGenerateContractParameters.getApiPersons()) {
                ContractEntity tmpContractEntity = eSealAccountManagerService.getPersonId(apiPerson);
                if (apiPerson.getIdNumber().equals(apiParameter.getIdNumberOrEnterpriserCode())) {
                    contractEntity = tmpContractEntity;
                }
                contractEntityPartyDao.create(new ContractEntityParty(tmpContractEntity, contract, apiPerson.getKeyword()));
            }
            // 维护合同与企业之间的关系
            List<ApiEnterprise> apiEnterprises = apiGenerateContractParameters.getApiEnterprises();

            // 把壹佰金融平台信息添加进来
            apiEnterprises = parameterManagerService.getAllApiEnterprises(apiEnterprises);
            for (int i = 0; apiEnterprises != null && i < apiEnterprises.size(); i++) {
                ApiEnterprise apiEnterprise = apiEnterprises.get(i);
                ContractEntity tmpContractEntity = eSealAccountManagerService.getEnterpriseId(apiEnterprise);
                if (apiEnterprise.getEnterpriseCode().equals(apiParameter.getIdNumberOrEnterpriserCode())) {
                    contractEntity = tmpContractEntity;
                }
                contractEntityPartyDao.create(new ContractEntityParty(tmpContractEntity, contract, apiEnterprise.getKeyword()));
            }
            contractDao.udpateContract(contract.getId(), contractEntity, jsonMapper.toJson(apiParameter.getApiGenerateContractParameters()), ContractStatus.WAIT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            contractDao.udpateStatus(contract.getId(), ContractStatus.FAIL, fileId, e.getMessage());
        }
    }

    public void deleteContractEntityParty(Long contractId) {
        List<ContractEntityParty> contractEntityPartys = contractEntityPartyDao.getContractEntityPartys(contractId);
        for (int i = 0;contractEntityPartys != null && i < contractEntityPartys.size(); i++) {
            contractEntityPartyDao.delete(contractEntityPartys.get(i));
        }
    }
}
