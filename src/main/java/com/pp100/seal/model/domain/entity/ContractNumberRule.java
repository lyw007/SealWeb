package com.pp100.seal.model.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pp100.model.domain.entity.IdEntity;

@DynamicUpdate
@DynamicInsert
@Entity
public class ContractNumberRule extends IdEntity<ContractNumberRule> {
    private int contractNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    ContractNumberRule() {
    }

    public ContractNumberRule(int contractNumber, Date createTime) {
        this.contractNumber = contractNumber;
        this.createTime = createTime;
    }

    public int getContractNumber() {
        return contractNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setContractNumber(int contractNumber) {
        this.contractNumber = contractNumber;
    }
}
