package com.pp100.seal.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.entity.ContractEntityParty;

/**
 * 合同管理-DAO
 * 
 * @author Administrator
 */
@Repository
public class ContractEntityPartyDao extends IdEntityDao<ContractEntityParty> {
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<ContractEntityParty> getContractEntityPartys(Long contractId) {
        return readOnlyQuery(" from ContractEntityParty where contract.id = :contractId ").setParameter("contractId", contractId).list();
    }
}
