package com.pp100.seal.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.entity.ContractTemplate;

@Repository
public class ContractTemplateDao extends IdEntityDao<ContractTemplate> {
    /**
     * 查询所有的合同信息
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<ContractTemplate> getAllList() {
        return readOnlyQuery(" from ContractTemplate ").list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ContractTemplate getContractTemplateByTemplateName(String templateName) {
        return (ContractTemplate)readOnlyQuery(" from ContractTemplate where templateName= :templateName ").setParameter("templateName", templateName).uniqueResult();
    }
}
