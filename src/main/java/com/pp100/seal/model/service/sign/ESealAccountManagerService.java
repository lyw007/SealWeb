package com.pp100.seal.model.service.sign;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.model.dao.ContractBatchDao;
import com.pp100.seal.model.dao.ContractDao;
import com.pp100.seal.model.dao.ContractEntityDao;
import com.pp100.seal.model.dao.ContractEntityPartyDao;
import com.pp100.seal.model.domain.entity.ContractEntity;
import com.pp100.seal.model.sealutil.ESignInit;
import com.pp100.seal.model.sealutil.EsignAccountService;
import com.pp100.seal.model.service.template.ParameterManagerService;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;

/**
 * 用户在E签宝认证的账号管理
 * 
 * @author Administrator
 *
 */
@Service
public class ESealAccountManagerService {
    private static Logger logger = Logger.getLogger(ESealAccountManagerService.class);

    private ContractEntityDao contractEntityDao;
    private EsignAccountService esignAccountService;

    @Autowired
    public ESealAccountManagerService(ContractEntityDao contractEntityDao, EsignAccountService esignAccountService) {
        this.contractEntityDao = contractEntityDao;
        this.esignAccountService = esignAccountService;
    }

    /**
     * 获取用户在E签宝认证账号(如果存在，添加账号，并返回认账证号)
     * 
     * @param apiPerson
     * @return
     */
    public ContractEntity getPersonId(ApiPerson apiPerson) throws Exception {
        String openId = ESignInit.getOpenId();
        ContractEntity contractPerson = contractEntityDao.getContractPerson(apiPerson.getIdNumber());
        if (contractPerson != null) {
            return contractPerson;
        }

        return this.addPerson(openId, apiPerson);
    }

    /**
     * 获取企业在E签宝认证账号(如果存在，添加账号，并返回认账证号)
     * 
     * @param apiOrg
     * @return
     */
    public ContractEntity getEnterpriseId(ApiEnterprise apiEnterprise) throws Exception {
        String openId = ESignInit.getOpenId(); // 获取开发者ID
        ContractEntity contractEnterprise = contractEntityDao.getContractEnterprise(apiEnterprise.getEnterpriseCode());
        if (contractEnterprise != null) {
            return contractEnterprise;
        }

        return addEnterprise(openId, apiEnterprise);
    }

    /**
     * 添加个人信息
     * 
     * @param openId
     * @param apiPerson
     * @return
     */
    private ContractEntity addPerson(String openId, ApiPerson apiPerson) throws Exception {
        String name = StringUtils.trimToEmpty(apiPerson.getName());
        String idNumber = StringUtils.trimToEmpty(apiPerson.getIdNumber());
        String mobile = StringUtils.trimToEmpty(apiPerson.getMobile());

        AddAccountResult accountResult = esignAccountService.addPerson2AccountId(openId, name, idNumber, mobile);
        // 不仅仅是这个错误需要检查
        if (accountResult.getErrCode() == 1006) {// 用户已经存在
            logger.error("===用户已经存在===" + apiPerson.getIdNumber() + "\t" + apiPerson.getName() + "\t"
                    + apiPerson.getMobile());
            throw new Exception("===用户已经存在===" + apiPerson.getIdNumber() + "\t" + apiPerson.getName() + "\t"
                    + apiPerson.getMobile());
        }

        if (accountResult.getErrCode() != 0) {
            throw new Exception("===用户创建失败===" + apiPerson.getIdNumber() + "\t" + apiPerson.getName() + "\t"
                    + apiPerson.getMobile());
        }
        Assert.isTrue(!StringUtils.isBlank(accountResult.getAccountId()));
        return contractEntityDao.addPersonIfNotExists(accountResult.getAccountId(), name, idNumber, mobile);
    }

    /**
     * 添加企业信息
     * 
     * @param openId
     * @param apiOrg
     * @return
     */
    private ContractEntity addEnterprise(String openId, ApiEnterprise apiEnterprise) throws Exception {
        EsignAccountService account = new EsignAccountService();
        String name = StringUtils.trimToEmpty(apiEnterprise.getName());
        String mobile = StringUtils.trimToEmpty(apiEnterprise.getMobile());

        int enterpriseCodeType = apiEnterprise.getEnterpriseCodeType().ordinal();
        String enterpriseCode = StringUtils.trimToEmpty(apiEnterprise.getEnterpriseCode()); // 组织机构代码或统一社会信用号

        int enterpriseUserType = apiEnterprise.getEnterpriseUserType();
        String representativeAgentName = StringUtils.trimToEmpty(apiEnterprise.getRepresentativeAgentName());
        String representativeAgentIdNumber = StringUtils.trimToEmpty(apiEnterprise.getRepresentativeAgentIdNumber());

        // 法人/代理人
        String legalName = 2 == enterpriseUserType ? representativeAgentName : null;
        String legalIdNo = 2 == enterpriseUserType ? representativeAgentIdNumber : null;
        String agentName = 1 == enterpriseUserType ? representativeAgentName : null;
        String agentIdNo = 1 == enterpriseUserType ? representativeAgentIdNumber : null;

        String email = StringUtils.trimToEmpty(apiEnterprise.getEmail());
        int legalArea = apiEnterprise.getLegalArea();
        int organType = apiEnterprise.getEnterpriseType();

        AddAccountResult accountResult = account.addOrganize2AccountId(openId, name, mobile, enterpriseCode, legalName, legalIdNo,
                legalArea, enterpriseUserType, agentName, agentIdNo, email, organType, null);
        if (accountResult.getErrCode() == 1006) {// 用户已经存在
            throw new Exception("===企业已经存在===" + apiEnterprise.getEnterpriseCode() + "\t" + apiEnterprise.getName()
                    + "\t" + apiEnterprise.getMobile());
        }
        if (accountResult.getErrCode() != 0) {
            throw new Exception("===企业创建失败===" + apiEnterprise.getEnterpriseCode() + "\t" + apiEnterprise.getName()
            + "\t" + apiEnterprise.getMobile());
        }
        Assert.isTrue(!StringUtils.isBlank(accountResult.getAccountId()));
        try {
            return contractEntityDao.create(new ContractEntity(accountResult.getAccountId(), legalName, enterpriseCodeType,
                    enterpriseCode, mobile, enterpriseUserType, representativeAgentName, representativeAgentIdNumber));
        } catch (Exception e) { // 已经添加，查询
            return contractEntityDao.getContractEnterprise(enterpriseCode);
        }

    }
}
