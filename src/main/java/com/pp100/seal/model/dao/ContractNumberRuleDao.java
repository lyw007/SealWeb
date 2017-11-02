package com.pp100.seal.model.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.entity.ContractNumberRule;

@Repository
public class ContractNumberRuleDao extends IdEntityDao<ContractNumberRule> {

    /**
     * 查询所有的合同信息
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public ContractNumberRule getContractNumberRule() {
        return (ContractNumberRule) readOnlyQuery(" from ContractNumberRule ").uniqueResult();
    }

    public ContractNumberRule update(Long contractNumberRuleId, int newContractNumber) {
        ContractNumberRule contractNumberRule = (ContractNumberRule) lock(contractNumberRuleId);
        ContractNumberRule base = contractNumberRule.clone();
        contractNumberRule.setContractNumber(newContractNumber);
        return safeUpdate(base, contractNumberRule);
    }

    public ContractNumberRule add(int createNumber, Date createDate) {
        return create(new ContractNumberRule(createNumber, createDate));
    }

}
