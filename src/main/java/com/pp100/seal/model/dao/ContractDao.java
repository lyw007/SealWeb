package com.pp100.seal.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.ContractStatus;
import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.domain.entity.ContractEntity;

/**
 * 合同管理-DAO
 *  
 * @author Administrator
 */
@Repository 
public class ContractDao extends IdEntityDao<Contract> {
    
    /**
     * 查询合同信息
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Contract getContract(Long contractBatchId, String checkNumber) {
        return (Contract) readOnlyQuery(
                " from Contract where contractBatch.id = :contractBatchId and checkNumber = :checkNumber")
                        .setParameter("contractBatchId", contractBatchId).setParameter("checkNumber", checkNumber)
                        .uniqueResult();
    }

    /**
     * 查询所有的合同信息
     * 
     * @return
     */ 
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Contract> getAllWaitSealContractList() {
        return readOnlyQuery(" from Contract where status = :status ")
                  .setParameter("status", ContractStatus.WAIT).list();
    }

    /**
     * 查询所有的合同信息
     * 
     * @return
     */ 
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Contract getContractBySoleCode(String soleCode) {
        return (Contract)readOnlyQuery(" from Contract where soleCode = :soleCode ").setParameter("soleCode", soleCode).setMaxResults(1).uniqueResult();
    }

    
    /**
     * 查询所有的合同信息(状态：等待)
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Contract> getWAITSealContractList() {
        return readOnlyQuery(" from Contract where status = :status order by time desc").setParameter("status", ContractStatus.WAIT).setFirstResult(0).setMaxResults(300).list();
    }
    
    /**
     * 查询所有的合同信息(状态：失败)
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Contract> getFAILSealContractList() {
        return readOnlyQuery(" from Contract where status = :status order by time desc").setParameter("status", ContractStatus.FAIL).setFirstResult(0).setMaxResults(300).list();
    }
    
    public Contract udpateStatus(long contractId, ContractStatus status, String fileId, String remark) {
        Contract contract = (Contract) lock(contractId);
        Contract base = contract.clone();
        contract.setStatus(status);
        contract.setFileId(fileId);
        contract.setRemark(remark);
        return safeUpdate(base, contract);
    }
    
    public Contract udpateStatus(long contractId, ContractStatus status) {
        Contract contract = (Contract) lock(contractId);
        Contract base = contract.clone();
        contract.setStatus(status);
        return safeUpdate(base, contract);
    }
    
    public Contract udpateContractEntityParty(Long contractId, ContractEntity contractEntity) {
        Contract contract = (Contract) lock(contractId);
        Contract base = contract.clone();
        contract.setContractEntity(contractEntity);
        return safeUpdate(base, contract);
    }
    
    public Contract udpateContract(Long contractId, ContractEntity contractEntity, String contractParamters, ContractStatus status) {
        Contract contract = (Contract) lock(contractId);
        Contract base = contract.clone();
        contract.setContractEntity(contractEntity);
        contract.setParameters(contractParamters);
        contract.setStatus(status);
        return safeUpdate(base, contract);
    }
    
    public Contract udpatePdfUrl(Long contractId, String pdfUrl) {
        Contract contract = (Contract) lock(contractId);
        Contract base = contract.clone();
        contract.setPdfUrl(pdfUrl);
        return safeUpdate(base, contract);
    }
}
