package com.pp100.seal.model.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.EntityType;
import com.pp100.seal.model.domain.entity.ContractEntity;

/**
 * 合同管理-DAO
 * 
 * @author Administrator
 */
@Repository 
public class ContractEntityDao extends IdEntityDao<ContractEntity> {
    /**
     * 根据组织机构代码查询用户企业信息
     * 
     * @param enterpriseCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ContractEntity getContractEnterprise(String enterpriseCode) {
        return (ContractEntity) readOnlyQuery(" from ContractEntity where enterpriseCode = :enterpriseCode").setParameter("enterpriseCode", enterpriseCode).uniqueResult();
    }
    
    /**
     * 根据身份证号查询用户企业信息
     * 
     * @param enterpriseCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ContractEntity getContractPerson(String idNumber) {
        return (ContractEntity) readOnlyQuery(" from ContractEntity where idNumber = :idNumber").setParameter("idNumber", idNumber).uniqueResult();
    }
    
    /**
     * 根据身份证号查询用户企业信息
     * 
     * @param enterpriseCode
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ContractEntity getByEsignId(String esignId) {
        return (ContractEntity) readOnlyQuery(" from ContractEntity  where esignId = :esignId ").setParameter("esignId", esignId).uniqueResult();
    }

    
    
    /**
     * 添加个人信息
     * 
     * @param esignId
     * @param name
     * @param idNumber
     * @param mobile
     * @return
     */
    public ContractEntity addPersonIfNotExists(String esignId, String name, String idNumber, String mobile) {
        new AdvisoryLocker().add(esignId).tryLock();
        ContractEntity entityInDb = this.getByEsignId(esignId);
        if (entityInDb != null) {
            return entityInDb;
        }
        return this.create(new ContractEntity(esignId, name, idNumber, mobile));
    }
}
