package com.pp100.seal.model.service.sign;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pp100.seal.model.dao.ContractDao;
import com.pp100.seal.model.dao.ContractBatchDao;
import com.pp100.seal.model.domain.ContractBatchStatus;
import com.pp100.seal.model.domain.ContractStatus;
import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.domain.entity.ContractBatch;

@Service
public class ContractService {
    private static Logger logger = Logger.getLogger(ContractService.class);

    private ContractDao contractDao;
    private ContractBatchDao contractBatchDao;
    
    @Autowired
    public ContractService(ContractDao contractDao, ContractBatchDao contractBatchDao) {
        this.contractDao = contractDao;
        this.contractBatchDao = contractBatchDao;
    } 
    
    /**
     * 获取合同的访问地址
     * 
     * @param contentId
     * @return
     */
    public Contract getContract(long contractId) {
        return contractDao.get(contractId);
    }
    
    public ContractBatch getContractBatch(long contractBatchId) {
        return contractBatchDao.get(contractBatchId);
    }
    
    /**
     * 获取合同-PDF(未签章)的合同
     * 
     * @param contentId
     * @return
     */
    public String getPdfUrl(long contractId) {
        return contractDao.get(contractId).getPdfUrl();
    }
    
    public List<Contract> getWAITSealContractList() {
        return contractDao.getWAITSealContractList();
    }
    
    public List<Contract> getFAILSealContractList() {
        return contractDao.getFAILSealContractList();
    }
    
    public List<ContractBatch> getFAILSealBatchList() {
        return contractBatchDao.getContractBatchFailList();
    }
    
    public Contract updateStatus(Contract contract, String parameters) {
        if (contract.getStatus() == ContractStatus.SUCCESS) {
            return contract;
        }
        Contract base = contract.clone();
        contract.setStatus(ContractStatus.WAIT);
        contract.setParameters(parameters);
        return contractDao.safeUpdate(base, contract);
    }
    
    public ContractBatch updateContractBatchStatus(ContractBatch contractBatch, String parameters) {
        if (contractBatch.getStatus() == ContractBatchStatus.SUCCESS) {
            return contractBatch;
        }
        ContractBatch base = contractBatch.clone();
        contractBatch.setStatus(ContractBatchStatus.WAIT);
        contractBatch.setParameters(parameters);
        return contractBatchDao.safeUpdate(base, contractBatch);
    }
}