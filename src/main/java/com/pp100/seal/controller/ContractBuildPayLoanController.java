package com.pp100.seal.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pp100.model.domain.JsonMapper;
import com.pp100.seal.api.contractautogeneration.ApiContractBuildOption;
import com.pp100.seal.api.contractautogeneration.ApiContractPayLoan;
import com.pp100.seal.api.contractautogeneration.PayLoanContractType;
import com.pp100.seal.model.service.contractautogeneration.ContractBuildPayLoanService;
import com.pp100.seal.model.service.contractnumberrule.ContractNumberRuleService;

@Controller
@RequestMapping("/contractBuildPayLoan")
public class ContractBuildPayLoanController {
    private static Logger logger = Logger.getLogger(ContractBuildPayLoanController.class);
    private ContractBuildPayLoanService contractBuildPayLoanService;
    private ContractNumberRuleService contractNumberRuleService;
    private JsonMapper jsonMapper = new JsonMapper();
    @Autowired
    public ContractBuildPayLoanController(ContractBuildPayLoanService contractBuildSmartPayLoanService,
            ContractNumberRuleService contractNumberRuleService) {
        this.contractBuildPayLoanService = contractBuildSmartPayLoanService;
        this.contractNumberRuleService = contractNumberRuleService;
    }

    /**
     * 展示填写页面 -（小佰薪贷合同）
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "showContractBuildPayLoan")
    public ModelAndView showContractSmartPayLoan() {
        ModelAndView model = new ModelAndView("showContractBuildPayLoan");
        model.addObject("apiContractPayLoan", new ApiContractPayLoan());
        // 操作码表
        model.addObject("contractBuildOption", ApiContractBuildOption.getCodeMap());
        return model;
    }

    /**
     * 获取合同协议协议编号。（防止编号混淆）
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getProtocolNo")
    @ResponseBody
    public String getProtocolNo() throws Exception {
        return jsonMapper.toJson(contractNumberRuleService.getContractNumber());
    }

    /**
     * 创建合同信息（小佰薪贷合同）
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "downBatchContractSmartPayLoan")
    public ResponseEntity<byte[]> downBatchContractSmartPayLoan(@ModelAttribute ApiContractPayLoan apiContractPayLoan,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = contractBuildPayLoanService.getContractZipFile(apiContractPayLoan);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("创建合同信息（小佰薪贷合同）" + e.getMessage());
            return null;
        }
    }

    /**
     * 创建合同信息（小佰薪贷合同）
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "downOneContractPayLoan")
    public ResponseEntity<byte[]> downOneContractPayLoan(PayLoanContractType payLoanContractType,
            @ModelAttribute ApiContractPayLoan apiContractPayLoan, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            File file = contractBuildPayLoanService.downContractPDF(payLoanContractType, apiContractPayLoan);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("创建合同信息（小佰薪贷合同）" + e.getMessage());
            return null;
        }
    }
}
