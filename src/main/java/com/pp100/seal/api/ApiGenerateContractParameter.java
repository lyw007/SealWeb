package com.pp100.seal.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class ApiGenerateContractParameter {

    @ApiModelProperty(required = true, value = "合同待签字盖章的个人信息")
    private List<ApiPerson> apiPersons;

    @ApiModelProperty(required = true, value = "合同待签字盖章的企业信息")
    private List<ApiEnterprise> apiEnterprises;

    /**
     * 特别说明values固定中的参数
     * isPlatformSeal 是否对壹佰金融进行盖章
     * waterImage  默认是壹佰金融  waterImage=YBFKJ_WATER(壹佰分科技水印)
     */
    @ApiModelProperty(required = true, value = "合同参数值")
    private Map<String, Object> values = new HashMap<>();

    public ApiGenerateContractParameter() {
    }

    public ApiGenerateContractParameter(List<ApiPerson> apiPersons, List<ApiEnterprise> apiEnterprises,
            Map<String, Object> values) {
        this.apiPersons = apiPersons;
        this.apiEnterprises = apiEnterprises;
        this.values = values;
    }

    public List<ApiPerson> getApiPersons() {
        return apiPersons;
    }

    public void setApiPersons(List<ApiPerson> apiPersons) {
        this.apiPersons = apiPersons;
    }

    public List<ApiEnterprise> getApiEnterprises() {
        return apiEnterprises;
    }

    public void setApiEnterprises(List<ApiEnterprise> apiEnterprises) {
        this.apiEnterprises = apiEnterprises;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
