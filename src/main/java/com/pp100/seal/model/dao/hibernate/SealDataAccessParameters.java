package com.pp100.seal.model.dao.hibernate;

import java.util.Properties;

import org.springframework.stereotype.Component;

import com.pp100.model.dao.hibernate.DataAccessParameters;

@Component
public class SealDataAccessParameters implements DataAccessParameters {
    
    @Override
    public String entityPackage() {
        return "com.pp100.seal.model.domain.entity";
    }

    @Override
    public String clusterEntityPackage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Properties commonHibernateProperties() {
        return new Properties();
    }
}
