package com.pp100.seal.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class ApiParameter {
    /**
     * 1、由spring过来的 请求：流水编号规则： 标ID+"_"+身份证号
     */
    @ApiModelProperty(required = false, value = "唯一流水号")
    private String soleCode;

    @ApiModelProperty(required = true, value = "姓名或企业全称")
    private String name;
    @ApiModelProperty(required = true, value = "身份证号或企业住址机构代码、社会统一信用代码号")
    private String idNumberOrEnterpriserCode;
    @ApiModelProperty(required = true, value = "1：个人   0:企业")
    private ApiUserType userType;
    private ApiGenerateContractParameter apiGenerateContractParameters;

    public ApiParameter() {
    }

    public ApiParameter(String name, String idNumberOrEnterpriserCode, ApiUserType userType, String soleCode) {
        this.name = name;
        this.idNumberOrEnterpriserCode = idNumberOrEnterpriserCode;
        this.userType = userType;
        this.soleCode = soleCode;
    }

    public ApiParameter(String name, String idNumberOrEnterpriserCode, ApiUserType userType,
            ApiGenerateContractParameter apiGenerateContractParameters) {
        this.name = name;
        this.idNumberOrEnterpriserCode = idNumberOrEnterpriserCode;
        this.userType = userType;
        this.apiGenerateContractParameters = apiGenerateContractParameters;
    }

    public String getName() {
        return name;
    }

    public String getIdNumberOrEnterpriserCode() {
        return idNumberOrEnterpriserCode;
    }

    public ApiUserType getUserType() {
        return userType;
    }

    public ApiGenerateContractParameter getApiGenerateContractParameters() {
        return apiGenerateContractParameters;
    }

    public void setApiGenerateContractParameters(ApiGenerateContractParameter apiGenerateContractParameters) {
        this.apiGenerateContractParameters = apiGenerateContractParameters;
    }

    public String getSoleCode() {
        return soleCode;
    }
}
