package com.pp100.seal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.domain.entity.ContractBatch;
import com.pp100.seal.model.service.sign.ContractService;
import com.pp100.seal.model.service.sign.SealRecoveryManagerService;
import com.wordnik.swagger.annotations.Api;

@Controller
@RequestMapping("/sealResult")
@Api(value = "sealResult", description = "电子签章执行结果")
public class SealResultController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private SealRecoveryManagerService sealRecoveryManagerService;

    @RequestMapping(value = "/index")
    public ModelAndView showSealResultIndex() throws Exception {
        ModelAndView model = new ModelAndView("/showSealResultIndex");
        return model;
    }
    
    @RequestMapping(value = "/wait")
    public ModelAndView showWAITSealContractList() throws Exception {
        ModelAndView model = new ModelAndView("/showWait");
        List list = contractService.getWAITSealContractList();
        model.addObject("noSuccessSealContractList", list);
        model.addObject("size", list == null ? 0 : list.size());
        return model;
    }
    
    @RequestMapping(value = "/fail")
    public ModelAndView showFAILSealContractList() throws Exception {
        ModelAndView model = new ModelAndView("/showFail");
        List list = contractService.getFAILSealContractList();
        model.addObject("noSuccessSealContractList", list);
        model.addObject("size", list == null ? 0 : list.size());
        return model;
    }
    
    @RequestMapping(value = "/showEdit")
    public ModelAndView showEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Contract contract = contractService.getContract(Long.parseLong(request.getParameter("contractId")));
        if ("update".equals(request.getParameter("operateType"))) {
            contractService.updateStatus(contract, request.getParameter("parametersJson"));
        }
        ModelAndView model = new ModelAndView("/showEdit");
        model.addObject("contract", contract);
        return model;
    }
    
    @RequestMapping(value = "/batchFail")
    public ModelAndView showFAILSealBatchList() throws Exception {
        ModelAndView model = new ModelAndView("/showBatchFail");
        List list = contractService.getFAILSealBatchList();
        model.addObject("noSuccessSealBatchList", list);
        model.addObject("size", list == null ? 0 : list.size());
        return model;
    }
    
    @RequestMapping(value = "/showBatchEdit")
    public ModelAndView showBatchEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContractBatch contractBatch = contractService.getContractBatch(Long.parseLong(request.getParameter("contractBatchId")));
        if ("update".equals(request.getParameter("operateType"))) {
            contractService.updateContractBatchStatus(contractBatch, request.getParameter("parametersJson"));
            sealRecoveryManagerService.executeSeal(contractBatch);
        }
        ModelAndView model = new ModelAndView("/showBatchEdit");
        model.addObject("contractBatch", contractBatch);
        return model;
    }
}
