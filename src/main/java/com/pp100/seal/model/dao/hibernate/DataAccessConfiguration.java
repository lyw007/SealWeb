package com.pp100.seal.model.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.pp100.model.dao.hibernate.DataAccessParameters;
import com.pp100.model.dao.hibernate.SessionFactoryBuilder;
import com.pp100.model.dao.hibernate.TransactionManagerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DataAccessConfiguration {
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("hibernateProperties")
    private Properties hibernateProperties;

    @Autowired
    private DataAccessParameters dataAccessParameters;

    @Autowired(required = false)
    private TransactionManagerFactory transactionManagerFactory;

    @Bean
    public SessionFactory sessionFactory() throws SQLException {
        return new SessionFactoryBuilder(dataSource,
                dataAccessParameters.commonHibernateProperties(),
                hibernateProperties,
                dataAccessParameters.entityPackage()).buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws SQLException {
        HibernateTransactionManager transactionManager = TransactionManagerFactory.create(transactionManagerFactory, "TransactionManager");
        transactionManager.setSessionFactory(sessionFactory());
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate() throws SQLException {
        return new TransactionTemplate(transactionManager());
    }
}
