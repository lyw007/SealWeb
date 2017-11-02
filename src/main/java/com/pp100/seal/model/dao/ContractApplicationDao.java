package com.pp100.seal.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.entity.ContractApplication;

/**
 * 合同管理-DAO
 * 
 * @author Administrator
 */
@Repository
public class ContractApplicationDao extends IdEntityDao<ContractApplication> {

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<ContractApplication> getAllList() {
        return readOnlyQuery(" from ContractApplication ").list();
    }

    public ContractApplication getContractApplictionByApplictionName(String applicationName) {
        return (ContractApplication) readOnlyQuery(" from ContractApplication  where applicationName= :applicationName ")
                .setParameter("applicationName", applicationName).uniqueResult();
    }
}
