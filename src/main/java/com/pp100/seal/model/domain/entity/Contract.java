package com.pp100.seal.model.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.pp100.model.domain.entity.IdEntity;
import com.pp100.seal.model.dao.hibernate.StringJsonUserType;
import com.pp100.seal.model.domain.ContractStatus;

@DynamicUpdate
@DynamicInsert
@Entity
@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = StringJsonUserType.class) })
public class Contract extends IdEntity<Contract> {

    @ManyToOne
    @JoinColumn(name = "contract_batch_id")
    private ContractBatch contractBatch;
 
    @OneToOne
    @JoinColumn(name = "contract_entity_id")
    private ContractEntity contractEntity;

    @Type(type = "StringJsonObject")
    private String parameters;

    private String fileId;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private String remark;

    private String pdfUrl;
    
    /**
     * 合同所有者
     * 用于异常数据恢复使用，其他时候无用; 存储:个人身份证号/企业组织机构代码/统一社会信用代码;
     * 逻辑：当出现异常的（E签宝提示用户已存在），恢复使用;
     */
    private String checkNumber;
    
    /**
     * 唯一标识：防止重复提交
     */
    private String soleCode;

    public Contract() {
    }

    public Contract(ContractBatch contractBatch, ContractEntity contractEntity, String parameters, String fileId,
            ContractStatus status, String checkNumber, String soleCode) {
        this.contractBatch = contractBatch;
        this.contractEntity = contractEntity;
        this.parameters = parameters;
        this.fileId = fileId;
        this.status = status;
        this.checkNumber = checkNumber;
        this.soleCode = soleCode;
    }

    public ContractBatch getContractBatch() {
        return contractBatch;
    }

    public ContractEntity getContractEntity() {
        return contractEntity;
    }

    public String getParameters() {
        return parameters;
    }

    public String getFileId() {
        return fileId;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setContractEntity(ContractEntity contractEntity) {
        this.contractEntity = contractEntity;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getSoleCode() {
        return soleCode;
    }

    public void setSoleCode(String soleCode) {
        this.soleCode = soleCode;
    }
}