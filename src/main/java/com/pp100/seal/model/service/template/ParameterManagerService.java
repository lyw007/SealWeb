package com.pp100.seal.model.service.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pp100.seal.api.ApiEnterprise;
import com.pp100.seal.api.ApiEnterpriseCodeType;

/**
 * 对于合同参数，设计原则是：一份合同，一份数据，都由调用方按照格式生成好
 * @author HCW
 * 20160-12-04
 */ 
@Service
public class ParameterManagerService {
    private static Logger logger = Logger.getLogger(ParameterManagerService.class);

    @Value("#{applicationProperties['eseal.enterprise.name']}")
    private String e_enterprise_name = "";// 公司全称
     
    @Value("#{applicationProperties['eseal.enterprise.codetype']}")
    private String e_enterprise_codetype = "ORGANIZATION_CODE";//1：公司组织机构代码(ORGANIZATION_CODE) 2：社会统一代码号(SOCIAL_CREDIT_CODE_NUMBER)
    
    @Value("#{applicationProperties['eseal.enterprise.code']}")
    private String e_enterprise_code = "";// 公司组织机构代码或社会统一代码号
    
    @Value("#{applicationProperties['eseal.enterprise.mobile']}")
    private String e_enterprise_mobile = "";// 公司联系电话
    
    @Value("#{applicationProperties['eseal.enterprise.keyword']}")
    private String e_enterprise_keyword = "";   // 替换关键字
    
    @Value("#{applicationProperties['eseal.enterprise.usertype']}")
    private String e_enterprise_usertype = "2"; // 用户类型： 1：代理人 2：法人代表
    
    @Value("#{applicationProperties['eseal.representative.agent.name']}")
    private String e_representative_agent_name = "";// 代理人名称/法人姓名
    
    @Value("#{applicationProperties['eseal.representative.agent.idnumber']}")
    private String e_representative_agent_idnumber = "";// 代理人身份证号/法人身份证号
    
    @Value("#{applicationProperties['eseal.enterprise.utext']}")
    private String e_enterprise_utext = ""; // 圆形印章中小字
    
    public String getJson() {
        String json = "[\"contractPersons\":[{\"name\":\"String,姓名\"}],\"contractEnterprise\":[{\"name\":\"String,姓名\"}],\"contractContent\":[{\"name\":\"String,姓名\"}]]";
        return json;
    }

    /**
     * 获取所有的企业信息（壹佰平台默认）
     * 
     * @param apiOrgList
     * @return
     */
    public List<ApiEnterprise> getAllApiEnterprises(List<ApiEnterprise> apiEnterprises) {
        if(apiEnterprises == null){
            apiEnterprises = new ArrayList<ApiEnterprise>();
        }
        apiEnterprises.add(new ApiEnterprise(this.e_enterprise_name, this.e_enterprise_mobile, ApiEnterpriseCodeType.valueOf(this.e_enterprise_codetype), this.e_enterprise_code,
                Integer.parseInt(this.e_enterprise_usertype), this.e_representative_agent_name, this.e_representative_agent_idnumber, this.e_enterprise_utext,
                0, null,0, this.e_enterprise_keyword));
        return apiEnterprises;
    }
}
