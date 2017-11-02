package com.pp100.seal.model.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.pp100.model.domain.entity.IdEntity;

@DynamicUpdate 
@DynamicInsert
@Entity
public class ContractApplication extends IdEntity<ContractApplication> {
    
    @ManyToOne
    @JoinColumn(name="template_id")
    private ContractTemplate template;

    /** 用于外部查询使用 */
    private String applicationName;

    private boolean sign;

    /** 备注解释 */
    private String remarks;

    public ContractApplication() {
    }

    public ContractApplication(ContractTemplate template, String applicationName, boolean sign, String remarks) {
        super();
        this.template = template;
        this.applicationName = applicationName;
        this.sign = sign;
        this.remarks = remarks;
    }

    public ContractTemplate getTemplate() {
        return template;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public boolean isSign() {
        return sign;
    }

    public String getRemarks() {
        return remarks;
    }
}
