package com.pp100.seal.model.service.sign;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pp100.model.domain.JsonMapper;
import com.pp100.seal.api.ApiGenerateContractParameter;
import com.pp100.seal.model.dao.ContractDao;
import com.pp100.seal.model.domain.ContractStatus;
import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.service.template.TemplateManagerService;

@Service
public class SealContractTask {
    private static Logger logger = Logger.getLogger(SealContractTask.class);
    private JsonMapper jsonMapper = new JsonMapper();

    private ContractDao contractDao;
    private TemplateManagerService templateManagerService;

    @Autowired
    public SealContractTask(ContractDao contractDao, TemplateManagerService templateManagerService) {
        this.contractDao = contractDao;
        this.templateManagerService = templateManagerService;
    }

    private final ReentrantLock sealLock = new ReentrantLock();

    // 每1分钟执行一次，对回款预约标进行投资处理
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        boolean isLock = sealLock.tryLock();
        if (!isLock) {
            logger.info("正在执行电子盖章操作");
            return;
        }
        try {
            templateManagerService.clearTempSealContractFile();
            executeSealContractTask();
            logger.info("SealContractTaskJob=定时执行任务");
        } catch (Throwable e) {
            logger.error("正在执行电子盖章操作出现异常:", e);
        } finally {
            sealLock.unlock();
        }
    }

    /**
     * 执行定时任务
     */
    public void executeSealContractTask() {
        // 读取合同列表
        List<Contract> contracts = contractDao.getAllWaitSealContractList();
        for (Contract contract : contracts) {
            executeSealContract(contract);
        }
    }

    /**
     * 生成合同
     * 
     * @param contract
     */
    private boolean executeSealContract(Contract contract) {
        long contractId = contract.getId();
        String templateContract = contract.getContractBatch().getTemplate().getTemplateContent();
        try {
            ApiGenerateContractParameter apiGenerateContractParameter = jsonMapper.fromJson(contract.getParameters(),
                    new TypeReference<ApiGenerateContractParameter>() {
                    });

            // 执行成功并修改状态
            String fileId = templateManagerService.excuteContractBuildReturnFileId(contractId, templateContract,
                    (ArrayList) apiGenerateContractParameter.getValues().get("contractList"),
                    apiGenerateContractParameter.getValues(), apiGenerateContractParameter.getApiEnterprises(),
                    apiGenerateContractParameter.getApiPersons());
            contractDao.udpateStatus(contractId, ContractStatus.SUCCESS, fileId, "");
        } catch (Exception e) {
            logger.error("执行合同生成错误！ 合同编号=" + contractId + "\t" + e.getMessage(), e);
            contractDao.udpateStatus(contractId, ContractStatus.FAIL, null, e.getMessage());
        }
        return true;
    }
}
