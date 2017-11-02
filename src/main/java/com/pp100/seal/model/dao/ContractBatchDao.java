package com.pp100.seal.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pp100.model.dao.base.IdEntityDao;
import com.pp100.seal.model.domain.ContractBatchStatus;
import com.pp100.seal.model.domain.entity.ContractBatch;

/**
 * 合同管理-DAO
 * 
 * @author Administrator
 */ 
@Repository
public class ContractBatchDao extends IdEntityDao<ContractBatch> {

    /**
     * 查询所有的合同批次未成功的列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<ContractBatch> getContractBatchNoSuccessList() {
        return readOnlyQuery("from ContractBatch where status = :status")
                .setParameter("status", ContractBatchStatus.WAIT)
                .list();
    }

    /**
     * 查询所有的合同批次状态是失败的列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<ContractBatch> getContractBatchFailList() {
        return readOnlyQuery("from ContractBatch where status = :status")
                .setParameter("status", ContractBatchStatus.FAIL)
                .list();
    }

    
    public ContractBatch udpateStatus(Long contractBatchId, ContractBatchStatus contractBatchStatus){
        ContractBatch contractBatch = (ContractBatch) lock(contractBatchId);
        ContractBatch base = contractBatch.clone();
        contractBatch.setStatus(contractBatchStatus);
        return safeUpdate(base, contractBatch);
    }
}
