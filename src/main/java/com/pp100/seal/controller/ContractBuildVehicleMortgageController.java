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
import com.pp100.seal.api.contractautogeneration.ApiContractVehicleMortage;
import com.pp100.seal.api.contractautogeneration.VehicleMortageContractType;
import com.pp100.seal.model.service.contractautogeneration.ContractBuildVehicleMortgageService;
import com.pp100.seal.model.service.contractnumberrule.ContractNumberRuleService;

@Controller
@RequestMapping("/contractBuildVehicleMortgage")
public class ContractBuildVehicleMortgageController {
    private static Logger logger = Logger.getLogger(ContractBuildVehicleMortgageController.class);
    private ContractBuildVehicleMortgageService contractBuildVehicleMortgageService;
    private ContractNumberRuleService contractNumberRuleService;
    private JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    public ContractBuildVehicleMortgageController(
            ContractBuildVehicleMortgageService contractBuildVehicleMortgageService,
            ContractNumberRuleService contractNumberRuleService) {
        this.contractBuildVehicleMortgageService = contractBuildVehicleMortgageService;
        this.contractNumberRuleService = contractNumberRuleService;
    }

    /**
     * 展示填写页面 -（车辆业务-抵押业务合同）
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "showContractBuildVehicleMortgage")
    public ModelAndView showContractVehicleMortgage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("showContractBuildVehicleMortgage");
        model.addObject("apiContractVehicleMortage", new ApiContractVehicleMortage());
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
     * 创建合同信息
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "downBatchContractVehicleMortgage")
    public ResponseEntity<byte[]> downBatchContractVehicleMortgage(
            @ModelAttribute ApiContractVehicleMortage apiContractVehicleMortage, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            File file = contractBuildVehicleMortgageService.getContractZipFile(apiContractVehicleMortage);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("创建合同信息", e);
            return null;
        }
    }

    /**
     * 创建合同信息
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "downOneContract")
    public ResponseEntity<byte[]> downOneContract(VehicleMortageContractType vehicleMortageContractType,
            @ModelAttribute ApiContractVehicleMortage apiContractVehicleMortage, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            File file = contractBuildVehicleMortgageService.downContractPDF(vehicleMortageContractType,
                    apiContractVehicleMortage);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment",
                    new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("创建合同信息 ", e);
            return null;
        }
    }
}
