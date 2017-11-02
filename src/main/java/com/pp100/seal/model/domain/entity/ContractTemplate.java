package com.pp100.seal.model.domain.entity;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.pp100.model.domain.entity.IdEntity;
import com.pp100.seal.model.dao.hibernate.StringJsonUserType;

@DynamicUpdate
@DynamicInsert
@Entity
@TypeDefs({ @TypeDef(name = "StringJsonObject", typeClass = StringJsonUserType.class) })
public class ContractTemplate extends IdEntity<ContractTemplate> {

    /** 平台合同模板名称 **/
    private String templateName;

    private String templateContent;

    @Type(type = "StringJsonObject")
    private String paramters;

    public ContractTemplate() {
    }

    public ContractTemplate(String templateName, String templateContent, String paramters) {
        this.templateName = templateName;
        this.paramters = paramters;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getParamters() {
        return paramters;
    }

    public String getTemplateContent() {
        return templateContent;
    }
}
