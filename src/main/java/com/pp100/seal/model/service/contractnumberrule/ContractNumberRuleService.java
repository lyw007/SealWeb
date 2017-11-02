package com.pp100.seal.model.service.contractnumberrule;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pp100.seal.model.dao.ContractNumberRuleDao;
import com.pp100.seal.model.domain.entity.ContractNumberRule;

@Service
public class ContractNumberRuleService {
    private static Logger logger = Logger.getLogger(ContractNumberRuleService.class);

    private ContractNumberRuleDao contractNumberRuleDao;

    @Autowired
    public ContractNumberRuleService(ContractNumberRuleDao contractNumberRuleDao) {
        this.contractNumberRuleDao = contractNumberRuleDao;
    }

    /**
     * 获取最新的合同编号
     * 
     * @return
     */
    public synchronized String getContractNumber() {
        String newContractNumber = "00000" + getNewContractNumber();
        String nowTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return new StringBuffer("YBJR").append(nowTime)
                .append(newContractNumber.substring(newContractNumber.length() - 5, newContractNumber.length()))
                .toString();
    }

    /**
     * 获取最新的合同编号
     * 
     * @return
     */
    private int getNewContractNumber() {
        return getTheSameDayContractNumberRule().getContractNumber();
    }

    /**
     * 获取当天合同编号规则
     * 
     * @return
     */
    private ContractNumberRule getTheSameDayContractNumberRule() {
        int startContractNumber = 0;
        ContractNumberRule contractNumberRule = contractNumberRuleDao.getContractNumberRule();
        if (contractNumberRule == null) {
            contractNumberRule = contractNumberRuleDao.add(startContractNumber, new Date());
        }else{
            contractNumberRule = contractNumberRuleDao.update(contractNumberRule.getId(), contractNumberRule.getContractNumber() + 1);
        }
        return contractNumberRule;
    }
}