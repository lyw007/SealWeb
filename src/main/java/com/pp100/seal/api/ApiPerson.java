package com.pp100.seal.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonInclude(Include.NON_NULL)
public class ApiPerson {
    @ApiModelProperty(required = true, value = "姓名")
    private String name;

    @ApiModelProperty(required = true, value = "身份证号")
    private String idNumber;

    @ApiModelProperty(required = true, value = "手机号")
    private String mobile;

    @ApiModelProperty(required = true, value = "合同模板替换关键字")
    private String keyword = "";

    public ApiPerson() {
    }
    
    public ApiPerson(String name, String idNumber, String mobile, String keyword) {
        this.name = name;
        this.idNumber = idNumber;
        this.mobile = mobile;
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public String getKeyword() {
        return keyword;
    }
}
