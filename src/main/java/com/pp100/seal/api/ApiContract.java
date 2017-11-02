package com.pp100.seal.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel 
@JsonInclude(Include.NON_NULL)
public class ApiContract {
    
    @ApiModelProperty(required = true, value = "文件服务器所在地址")
    private String fileId;
    @ApiModelProperty(required = true, value = "执行结果")
    private String remarks;
    @ApiModelProperty(required = true, value = "文件服务器访问地址")
    private String fileServerUrl;
    
    public ApiContract(String fileId, String remarks, String fileServerUrl) {
        this.fileId = fileId;
        this.remarks = remarks;
        this.fileServerUrl = fileServerUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public String getRemarks() {
        return remarks;
    }
}
