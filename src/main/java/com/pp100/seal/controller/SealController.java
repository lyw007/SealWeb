package com.pp100.seal.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pp100.model.domain.JsonMapper;
import com.pp100.seal.api.ApiContract;
import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiGenerateContractParameter;
import com.pp100.seal.api.ApiParameter;
import com.pp100.seal.api.ApiPerson;
import com.pp100.seal.api.ApiUserType;
import com.pp100.seal.model.domain.entity.Contract;
import com.pp100.seal.model.domain.entity.ContractApplication;
import com.pp100.seal.model.domain.entity.ContractTemplate;
import com.pp100.seal.model.service.sign.ContractService;
import com.pp100.seal.model.service.sign.SealManagerService;
import com.pp100.seal.model.service.template.TemplateManagerService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/seal")
@Api(value = "seal", description = "电子签章接口")
public class SealController {
    private static Logger logger = Logger.getLogger(SealController.class);
    @Value("#{applicationProperties['show.file.url']}")
    public String SHOW_FILE_URL = ""; // 文件查看地址
    @Autowired
    private TemplateManagerService templateManagerService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private SealManagerService sealManagerService;
    private JsonMapper jsonMapper = new JsonMapper();

    /**
     * 展示模板列表信息
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showTemplates")
    public ModelAndView showTemplates() throws Exception {
        ModelAndView model = new ModelAndView("/templates");
        model.addObject("templateApplictions", templateManagerService.getAllContractApplications()); //平台合同模板名称
        return model;
    }
    
    /**
     * 通过模板名称展示合同相关参数(线下合同)
     * 
     * @param contractTemplateFtl
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showOfflineTemplate")
    public ModelAndView showOfflineTemplate(String applicationName) throws Exception {
        ModelAndView model = new ModelAndView("showOfflineTemplate");
        try {
            if (StringUtils.isBlank(applicationName) || "".equals(applicationName)) {
                return new ModelAndView("redirect:/error.jsp");
            }
            ContractApplication contractApplication = templateManagerService.getContractApplictionByApplictionName(applicationName);
            ApiGenerateContractParameter apiGenerateContractParameter = jsonMapper.fromJson(contractApplication.getTemplate().getParamters(), new TypeReference<ApiGenerateContractParameter>() {});
            
            model.addObject("contractTemplateName", contractApplication.getApplicationName()); //平台合同模板名称
            model.addObject("apiGenerateContractParameter", apiGenerateContractParameter); //模板所需的参数
            
        } catch (Exception e) {
            logger.error("通过模板名称展示合同相关参数(线下合同)" + e.getMessage());
            return new ModelAndView("redirect:/error.jsp");
        }
        return model;
    }
    
    /**
     * 进行单个合同的生成（线下合同）
     * @param contractTemplateFtl
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toOfflineContract")
    public ResponseEntity<byte[]> createOfflineContract(String applicationName,String values) throws Exception {
        Map<String, Object> contractContents = values == null || values.length() < 10 ? new HashMap() : jsonMapper.fromJson(values, new TypeReference<Map<String, Object>>() {});
        List contractList = contractContents.get("contractList") == null ? new ArrayList() : (ArrayList)contractContents.get("contractList") ;
        
        File file = templateManagerService.getAgreementFile(templateManagerService.getContractApplictionByApplictionName(applicationName).getTemplate().getTemplateContent(), contractList, contractContents);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("UTF-8"), "iso-8859-1"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
    
    /**
     * 通过模板名称展示合同相关参数(线上合同)
     * @param contractTemplateFtl
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/showOnlineTemplate")
    public ModelAndView showOnlineTemplate(String applicationName) throws Exception {
        ModelAndView model = new ModelAndView("showOnlineTemplate");
        try{
            if(StringUtils.isBlank(applicationName)||"".equals(applicationName)){
                return new ModelAndView("redirect:/error.jsp");
            }
            ContractApplication contractApplication = templateManagerService.getContractApplictionByApplictionName(applicationName);
            ApiGenerateContractParameter apiGenerateContractParameter = jsonMapper.fromJson(contractApplication.getTemplate().getParamters(), new TypeReference<ApiGenerateContractParameter>() {});
            model.addObject("contractTemplateName", contractApplication.getApplicationName()); 
            model.addObject("apiGenerateContractParameter", apiGenerateContractParameter);
        }catch(Exception e){
            logger.error("通过模板名称展示合同相关参数有误"+e.getMessage());
            return new ModelAndView("redirect:/error.jsp");
        }
        return model;
    }

    /**
     * 进行单个合同的盖章处理
     * @param contractTemplateFtl
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toSealOnlineContract")
    public ModelAndView sealOnlineContract(String soleCode, String applicationName, String name, 
            String idNumberOrEnterpriserCode, String userType, String apipersonJson,
            String apienterpriseJson, String values) throws Exception {
        ModelAndView model = new ModelAndView("/showResult");
        try{
            List<ApiParameter> apiParameters = new ArrayList<ApiParameter>();
            ApiParameter apiParameter = new ApiParameter(name, idNumberOrEnterpriserCode, ApiUserType.valueOf(userType), soleCode);
            ApiGenerateContractParameter apiGenerateContractParameter = new ApiGenerateContractParameter();
            
            //合同主体参数（map及list列表）
            Map<String, Object> contractContents = values == null || values.length() < 10 ? new HashMap() : jsonMapper.fromJson(values, new TypeReference<Map<String, Object>>() {});
            List contractList = contractContents.get("contractList") == null ? new ArrayList() : (ArrayList)contractContents.get("contractList");  
            contractContents.put("contractList", contractList);
            apiGenerateContractParameter.setValues(contractContents);
            
            //合同盖章个人用户
            List<ApiPerson> apiPersons = apipersonJson == null || apipersonJson.length() < 10 ? new ArrayList<ApiPerson>() : jsonMapper.fromJson(apipersonJson, new TypeReference<List<ApiPerson>>() {}); 
            apiGenerateContractParameter.setApiPersons(apiPersons);
            
            //合同盖章企业用户
            List<ApiEnterprise> apiEnterprises=   apienterpriseJson == null || apienterpriseJson.length() < 10 ? new ArrayList<ApiEnterprise>() : jsonMapper.fromJson(apienterpriseJson, new TypeReference<List<ApiEnterprise>>() {});
            apiGenerateContractParameter.setApiEnterprises(apiEnterprises);
            
            apiParameter.setApiGenerateContractParameters(apiGenerateContractParameter);
            apiParameters.add(apiParameter);
            
            ContractTemplate contractTemplate = templateManagerService.getContractApplictionByApplictionName(applicationName).getTemplate();
            Map<String, Object> result = sealManagerService.executeSeal(contractTemplate , jsonMapper.toJson(apiParameters));
            model.addObject("contractId", jsonMapper.toJson(result.get(idNumberOrEnterpriserCode)));
        }catch(Exception e)
        {
            logger.error("传入的参数有误!");
            return new ModelAndView("redirect:/error.jsp");
        }
        return model;
    }
    
    @ResponseBody
    @RequestMapping(value = "/batchseal", method = RequestMethod.POST)
    @ApiOperation(value = "批量执行-电子盖章", notes = "批量执行-电子盖章")
    public String batchSealContract(@ApiParam(value = "模板名称") @RequestParam String applictionName,
            @ApiParam(value = "合同所需参数信息 JSON数组 格式：") @RequestParam(required = false) String parameters) throws Exception {
        if (StringUtils.isBlank(applictionName) || StringUtils.isBlank(parameters)) {
            return "参数不能为空!";
        }
        logger.info("电子签章执行" + applictionName);
        ContractApplication application = templateManagerService.getContractApplictionByApplictionName(applictionName);
        if (null == application) {
            return "模板无效!";
        }
        
        ContractTemplate contractTemplate = application.getTemplate();
        if (null == contractTemplate) {
            return "模板无效!";
        }
        
        return jsonMapper.toJson(sealManagerService.executeSeal(contractTemplate , parameters));
    }

    @RequestMapping(value = "/showContractById", method = RequestMethod.GET)
    @ResponseBody
    public String showContract(@ApiParam(value = "合同ID") @RequestParam String contractId,
            HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(contractId)) {
            return "参数非法";
        }
        Contract contract = contractService.getContract(Long.parseLong(contractId));
        return jsonMapper.toJson(new ApiContract(contract.getFileId(), contract.getRemark(), SHOW_FILE_URL + contract.getFileId()));
    }
    
    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET) 
    @ApiOperation(value = "批量执行-电子盖章", notes = "批量执行-电子盖章")
    public String test() throws Exception {
        String parameters = "[{\"name\":\"刘泽旭\",\"idNumberOrEnterpriserCode\":\"210403197406150618\",\"userType\":\"PERSON\",\"soleCode\":\"BID_7474_210403197406150618\",\"apiGenerateContractParameters\":{\"apiPersons\":[{\"name\":\"刘泽旭\",\"idNumber\":\"210403197406150618\",\"mobile\":\"13911527490\",\"keyword\":\"210403197406150618~\"}],\"apiEnterprises\":[{\"name\":\"合生创盈融资担保股份有限公司\",\"mobile\":\"13802788075\",\"enterpriseCodeType\":\"SOCIAL_CREDIT_CODE_NUMBER\",\"enterpriseCode\":\"91440101751095303N\",\"enterpriseUserType\":2,\"representativeAgentName\":\"吴祎\",\"representativeAgentIdNumber\":\"450404196610241214\",\"legalArea\":0,\"enterpriseType\":0,\"keyword\":\"合生创盈融资担保股份有限公司~\"}],\"values\":{\"overduePlatformApr\":1.00,\"loanType\":\"1\",\"currentIdNumber\":\"210403197406150618\",\"repaymentStr\":\"每月9日\",\"bidNo\":\"E42017050905\",\"periodUnit\":12,\"investAmountStr\":\"壹佰伍拾万元整\",\"advanceRateInvestor\":5,\"loanName\":\"广州****有限公司\",\"aprPlatformStatus\":0,\"investmentFee\":\"0.0\",\"agencyIdNumber\":\"440******1149\",\"protocolNo\":\"YBJR-JK-201705097474\",\"investRate\":0.000,\"beginTime\":\"2017-05-10\",\"advanceRatePlatform\":5,\"isLoan\":false,\"period\":12,\"amount\":\"1,500,000.00\",\"apr\":11.5000,\"currentUserName\":\"刘泽旭\",\"signTime\":\"2017年05月11日\",\"isAgencyEnterpriseCodeType\":false,\"overdueInvestorApr\":1.00,\"protocolType\":-1,\"assetNo\":\"\",\"loanIdNumber\":\"9************1\",\"bidId\":7474,\"agencyName\":\"合生创盈融资担保股份有限公司\",\"isLoanEnterpriseCodeType\":true,\"agencyAddress\":\"\",\"amountStr\":\"壹佰伍拾万元整\",\"periodStr\":\"12个月\",\"repaymentTypeId\":2,\"contractList\":[{\"id_number\":\"210403197406150618\",\"period\":12,\"amount\":\"2,492.00\",\"reality_name\":\"刘泽旭\",\"name\":\"liuzexu\",\"periodUnit\":\"月\"},{\"id_number\":\"510******3313\",\"period\":12,\"amount\":\"217.00\",\"reality_name\":\"魏**\",\"name\":\"y_1***372\",\"periodUnit\":\"月\"},{\"id_number\":\"430******1012\",\"period\":12,\"amount\":\"262.00\",\"reality_name\":\"曾**\",\"name\":\"zcf***015\",\"periodUnit\":\"月\"},{\"id_number\":\"359******1556\",\"period\":12,\"amount\":\"108.00\",\"reality_name\":\"王**\",\"name\":\"y_1***119\",\"periodUnit\":\"月\"},{\"id_number\":\"445******271X\",\"period\":12,\"amount\":\"298.00\",\"reality_name\":\"黄**\",\"name\":\"hhb***bxp\",\"periodUnit\":\"月\"},{\"id_number\":\"500******5230\",\"period\":12,\"amount\":\"400.00\",\"reality_name\":\"彭**\",\"name\":\"y_1***432\",\"periodUnit\":\"月\"},{\"id_number\":\"330******2419\",\"period\":12,\"amount\":\"580,531.00\",\"reality_name\":\"杨**\",\"name\":\"ylm***979\",\"periodUnit\":\"月\"},{\"id_number\":\"120******0518\",\"period\":12,\"amount\":\"786.00\",\"reality_name\":\"肖**\",\"name\":\"y_1***468\",\"periodUnit\":\"月\"},{\"id_number\":\"612******665X\",\"period\":12,\"amount\":\"59,483.00\",\"reality_name\":\"赵**\",\"name\":\"Yon***gli\",\"periodUnit\":\"月\"},{\"id_number\":\"130******106X\",\"period\":12,\"amount\":\"9,000.00\",\"reality_name\":\"邢**\",\"name\":\"y_1***990\",\"periodUnit\":\"月\"},{\"id_number\":\"420******1365\",\"period\":12,\"amount\":\"4,200.00\",\"reality_name\":\"杨**\",\"name\":\"tge***gel\",\"periodUnit\":\"月\"},{\"id_number\":\"513******0012\",\"period\":12,\"amount\":\"90,100.00\",\"reality_name\":\"戚**\",\"name\":\"y_1***988\",\"periodUnit\":\"月\"},{\"id_number\":\"510******3442\",\"period\":12,\"amount\":\"52,153.00\",\"reality_name\":\"车**\",\"name\":\"y_1***655\",\"periodUnit\":\"月\"},{\"id_number\":\"320******0030\",\"period\":12,\"amount\":\"19,237.00\",\"reality_name\":\"周**\",\"name\":\"169***609\",\"periodUnit\":\"月\"},{\"id_number\":\"441******3321\",\"period\":12,\"amount\":\"278.00\",\"reality_name\":\"莫**\",\"name\":\"sab***ina\",\"periodUnit\":\"月\"},{\"id_number\":\"441******5115\",\"period\":12,\"amount\":\"1,800.00\",\"reality_name\":\"李**\",\"name\":\"spc***630\",\"periodUnit\":\"月\"},{\"id_number\":\"320******5837\",\"period\":12,\"amount\":\"5,096.00\",\"reality_name\":\"周**\",\"name\":\"y_1***918\",\"periodUnit\":\"月\"},{\"id_number\":\"340******042X\",\"period\":12,\"amount\":\"1,200.00\",\"reality_name\":\"张**\",\"name\":\"y_1***767\",\"periodUnit\":\"月\"},{\"id_number\":\"622******1023\",\"period\":12,\"amount\":\"20,899.00\",\"reality_name\":\"张**\",\"name\":\"y_1***151\",\"periodUnit\":\"月\"},{\"id_number\":\"510******0010\",\"period\":12,\"amount\":\"67,018.00\",\"reality_name\":\"薛**\",\"name\":\"fin***ney\",\"periodUnit\":\"月\"},{\"id_number\":\"450******0028\",\"period\":12,\"amount\":\"265,800.00\",\"reality_name\":\"黄**\",\"name\":\"y_1***932\",\"periodUnit\":\"月\"},{\"id_number\":\"610******0805\",\"period\":12,\"amount\":\"213.00\",\"reality_name\":\"朱**\",\"name\":\"ker***915\",\"periodUnit\":\"月\"},{\"id_number\":\"410******2571\",\"period\":12,\"amount\":\"600.00\",\"reality_name\":\"李**\",\"name\":\"lis***ben\",\"periodUnit\":\"月\"},{\"id_number\":\"511******4884\",\"period\":12,\"amount\":\"245.00\",\"reality_name\":\"王**\",\"name\":\"y_1***051\",\"periodUnit\":\"月\"},{\"id_number\":\"610******0020\",\"period\":12,\"amount\":\"760.00\",\"reality_name\":\"朱**\",\"name\":\"y_1***632\",\"periodUnit\":\"月\"},{\"id_number\":\"360******0729\",\"period\":12,\"amount\":\"200.00\",\"reality_name\":\"陈**\",\"name\":\"dfh***302\",\"periodUnit\":\"月\"},{\"id_number\":\"511******0017\",\"period\":12,\"amount\":\"458.00\",\"reality_name\":\"李**\",\"name\":\"y_1***769\",\"periodUnit\":\"月\"},{\"id_number\":\"510******4419\",\"period\":12,\"amount\":\"15,019.00\",\"reality_name\":\"李**\",\"name\":\"y_1***569\",\"periodUnit\":\"月\"},{\"id_number\":\"320******0063\",\"period\":12,\"amount\":\"130,000.00\",\"reality_name\":\"陈**\",\"name\":\"y_1***788\",\"periodUnit\":\"月\"},{\"id_number\":\"330******2915\",\"period\":12,\"amount\":\"60,000.00\",\"reality_name\":\"池**\",\"name\":\"rac***cby\",\"periodUnit\":\"月\"},{\"id_number\":\"320******0029\",\"period\":12,\"amount\":\"100,000.00\",\"reality_name\":\"崔**\",\"name\":\"y_1***098\",\"periodUnit\":\"月\"},{\"id_number\":\"440******0010\",\"period\":12,\"amount\":\"10,000.00\",\"reality_name\":\"谢**\",\"name\":\"y_1***661\",\"periodUnit\":\"月\"},{\"id_number\":\"452******0319\",\"period\":12,\"amount\":\"1,147.00\",\"reality_name\":\"廖**\",\"name\":\"y_1***617\",\"periodUnit\":\"月\"}],\"addApr\":0.00,\"masterIdentity\":\"INVESTOR\",\"loanPurposeName\":\"借款企业申请融资总额760万元，用于购买货物，根据资金需求分期融资，现进行第一期融资150万元。\",\"investAmount\":\"1,500,000.00\",\"projectUse\":\"借款企业申请融资总额760万元，用于购买货物，根据资金需求分期融资，现进行第一期融资150万元。\",\"loanUserType\":1,\"contractUse\":\"购买货物\",\"endTime\":\"2018-05-09\",\"status\":\"REPAYING\"}}}]";
        
        ContractTemplate contractTemplate = templateManagerService.getContractApplictionByApplictionName("showProtocolHasGuaranteePDF.ftl").getTemplate();
        if (null == contractTemplate) {
            return "模板无效!";
        }
        return jsonMapper.toJson(sealManagerService.executeSeal(contractTemplate , parameters));
    }
}