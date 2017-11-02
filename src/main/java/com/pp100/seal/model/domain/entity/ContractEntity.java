package com.pp100.seal.model.domain.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pp100.model.domain.entity.IdEntity;
import com.pp100.seal.model.domain.EntityType;

/**
 * 合同盖章实体(保存了企业和个人信息)
 * 
 * @author HCW
 */
@DynamicUpdate 
@DynamicInsert
@Entity
public class ContractEntity extends IdEntity<ContractEntity> {
    private String name;
    private String idNumber;
    private String esignId;
    private String mobile;
    private int enterpriseUserType;
    private String representativeAgentName;
    private String representativeAgentIdNumber;
    private int enterpriseCodeType;
    private String enterpriseCode;
    @Enumerated(EnumType.STRING)
    private EntityType entityType; // 类型 1：个人 0：企业

    public ContractEntity() {
    }
    
    public static void main(String[] args){
        System.out.println(EnumType.ORDINAL);
    }

    /**
     * 用于个人
     * 
     * @param name
     * @param idNumber
     * @param esignId
     * @param mobile
     */
    public ContractEntity(String esignId, String name, String idNumber, String mobile) {
        this.name = name;
        this.idNumber = idNumber;
        this.esignId = esignId;
        this.mobile = mobile;
        this.entityType = EntityType.PERSON;
    }

    /**
     * 用于企业
     * 
     * @param name
     * @param idNumber
     * @param esignId
     * @param mobile
     */
    public ContractEntity(String esignId, String name, int enterpriseCodeType, String enterpriseCode, String mobile,
            int enterpriseUserType, String representativeAgentName, String representativeAgentIdNumber) {
        this.name = name;
        this.esignId = esignId;
        this.mobile = mobile;
        this.enterpriseUserType = enterpriseUserType;
        this.representativeAgentName = representativeAgentName;
        this.representativeAgentIdNumber = representativeAgentIdNumber;
        this.enterpriseCodeType = enterpriseCodeType;
        this.enterpriseCode = enterpriseCode;
        this.entityType = EntityType.COMPANY;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getEsignId() {
        return esignId;
    }

    public String getMobile() {
        return mobile;
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

    public int getEnterpriseCodeType() {
        return enterpriseCodeType;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
