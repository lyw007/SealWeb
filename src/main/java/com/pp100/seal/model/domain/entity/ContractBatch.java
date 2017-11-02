package com.pp100.seal.model.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.pp100.model.domain.entity.IdEntity;
import com.pp100.seal.model.dao.hibernate.StringJsonUserType;
import com.pp100.seal.model.domain.ContractBatchStatus;

@DynamicUpdate
@DynamicInsert
@Entity
@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = StringJsonUserType.class) })
public class ContractBatch extends IdEntity<ContractBatch> {

    @ManyToOne
    @JoinColumn(name = "template_id")
    private ContractTemplate template;

    @Type(type = "StringJsonObject")
    private String parameters;

    @Enumerated(EnumType.STRING)
    private ContractBatchStatus status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public ContractBatch() {
    }

    public ContractBatch(ContractTemplate template, String parameters, ContractBatchStatus status) {
        this.template = template;
        this.parameters = parameters;
        this.status = status;
    }

    public ContractTemplate getTemplate() {
        return template;
    }

    public String getParameters() {
        return parameters;
    }

    public ContractBatchStatus getStatus() {
        return status;
    }

    public void setStatus(ContractBatchStatus status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
