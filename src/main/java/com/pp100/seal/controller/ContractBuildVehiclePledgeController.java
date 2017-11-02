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
import com.pp100.seal.api.contractautogeneration.ApiContractVehiclePledge;
import com.pp100.seal.api.contractautogeneration.VehiclePledgeContractType;
import com.pp100.seal.model.service.contractautogeneration.ContractBuildVehiclePledgeService;
import com.pp100.seal.model.service.contractnumberrule.ContractNumberRuleService;

@Controller
@RequestMapping("/contractBuildVehiclePledge")
public class ContractBuildVehiclePledgeController {
    private static Logger logger = Logger.getLogger(ContractBuildVehiclePledgeController.class);
    private ContractBuildVehiclePledgeService contractBuildVehiclePledgeService;
    private ContractNumberRuleService contractNumberRuleService;
    private JsonMapper jsonMapper = new JsonMapper();

    @Autowired
    public ContractBuildVehiclePledgeController(ContractBuildVehiclePledgeService contractBuildVehiclePledgeService,
            ContractNumberRuleService contractNumberRuleService) {
        this.contractBuildVehiclePledgeService = contractBuildVehiclePledgeService;
        this.contractNumberRuleService = contractNumberRuleService;
    }

    /**
     * 展示填写页面 -（车辆业务-质押业务合同）
     * 
     * @param contractContents
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "showContractBuildVehiclePledge")
    public ModelAndView showContractVehiclePledge(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ModelAndView model = new ModelAndView("showContractBuildVehiclePledge");
        model.addObject("apiContractVehiclePledge", new ApiContractVehiclePledge());
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
    @RequestMapping(value = "downBatchContractVehiclePledge")
    public ResponseEntity<byte[]> downBatchContractVehiclePledge(
            @ModelAttribute ApiContractVehiclePledge apiContractVehiclePledge, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            File file = contractBuildVehiclePledgeService.getContractZipFile(apiContractVehiclePledge);
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
    public ResponseEntity<byte[]> downOneContract(VehiclePledgeContractType vehiclePledgeContractType,
            @ModelAttribute ApiContractVehiclePledge apiContractVehiclePledge, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            File file = contractBuildVehiclePledgeService.downContractPDF(vehiclePledgeContractType,
                    apiContractVehiclePledge);
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
}
