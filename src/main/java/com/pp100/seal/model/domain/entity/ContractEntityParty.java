package com.pp100.seal.model.domain.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pp100.model.domain.entity.IdEntity;

@DynamicUpdate
@DynamicInsert
@Entity
public class ContractEntityParty extends IdEntity<ContractEntityParty> {

    @ManyToOne
    private ContractEntity entity;

    @ManyToOne
    private Contract contract;

    private String keyword;

    public ContractEntityParty() {
    }

    public ContractEntityParty(ContractEntity entity, Contract contract, String keyword) {
        this.entity = entity;
        this.contract = contract;
        this.keyword = keyword;
    }

    public ContractEntity getEntity() {
        return entity;
    }

    public Contract getContract() {
        return contract;
    }

    public String getKeyword() {
        return keyword;
    }
}
