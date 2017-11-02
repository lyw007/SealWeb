package com.pp100.seal.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel 
@JsonInclude(Include.NON_NULL)
public class ApiEnterprise {
    @ApiModelProperty(required = true, value = "公司全称")
    private String name;

    @ApiModelProperty(required = true, value = "手机号")
    private String mobile;
   
    @ApiModelProperty(required = true, value = "代码类型： 1：组织机构代码  0：社会信用代码号")
    private ApiEnterpriseCodeType enterpriseCodeType;

    @ApiModelProperty(required = true, value = "组织机构代码/社会信用代码号")
    private String enterpriseCode;

    @ApiModelProperty(required = true, value = "企业用户类型  注册类型，1-代理人注册，2-法人注册，默认1")
    private int enterpriseUserType;

    @ApiModelProperty(required = true, value = "法人代表/代理人姓名")
    private String representativeAgentName;

    @ApiModelProperty(required = true, value = "法人身份证号/代理人身份证号")
    private String representativeAgentIdNumber;

    @ApiModelProperty(required = false, value = "电子签章图元层 例如：2016-12-09")
    private String plusDate;

    @ApiModelProperty(required = false, value = "法人所在地 法定代表人归属地,0-大陆，1-香港，2-澳门，3-台湾，4-外籍，默认0")
    private int legalArea;

    @ApiModelProperty(required = true, value = "邮箱")
    private String email;

    @ApiModelProperty(required = false, value = "公司类型 - 单位类型，0-普通企业，1-社会团体，2-事业单位，3-民办非企业单位，4-党政及国家机构，默认0 ")
    private int enterpriseType;

    @ApiModelProperty(required = true, value = "合同模板替换关键字")
    private String keyword = "";

    public ApiEnterprise() {
    }

    public ApiEnterprise(String name, String mobile, ApiEnterpriseCodeType enterpriseCodeType, String enterpriseCode,
            int enterpriseUserType, String representativeAgentName, String representativeAgentIdNumber, String plusDate,
            int legalArea, String email, int enterpriseType, String keyword) {
        this.name = name;
        this.mobile = mobile;
        this.enterpriseCodeType = enterpriseCodeType;
        this.enterpriseCode = enterpriseCode;
        this.enterpriseUserType = enterpriseUserType;
        this.representativeAgentName = representativeAgentName;
        this.representativeAgentIdNumber = representativeAgentIdNumber;
        this.plusDate = plusDate;
        this.legalArea = legalArea;
        this.email = email;
        this.enterpriseType = enterpriseType;
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public ApiEnterpriseCodeType getEnterpriseCodeType() {
        return enterpriseCodeType;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public int getEnterpriseUserType() {
        return enterpriseUserType;
    }

    public String getRepresentativeAgentName() {
        return representativeAgentName;
    }

    public String getRepresentativeAgentIdNumber() {
        return representativeAgentIdNumber;
    }

    public String getPlusDate() {
        return plusDate;
    }

    public int getLegalArea() {
        return legalArea;
    }

    public String getEmail() {
        return email;
    }

    public int getEnterpriseType() {
        return enterpriseType;
    }

    public String getKeyword() {
        return keyword;
    }
}
