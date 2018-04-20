/*
 * @(#)DatabaseConfig.java 1.0 09/09/2015
 *
 * Copyright (c) 2015, Embraer. All rights reserved. Embraer S/A
 * proprietary/confidential. Use is subject to license terms.
 */

package emonitor.app.database;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.orm.jpa.vendor.Database.H2;

/**
 * A classe <code>DatabaseConfig</code> contem a definicao de <i>beans</i> do
 * Spring referentes a configuracao de acesso a base de dados.
 *
 * @author Roberto Perillo
 * @version 1.0 09/09/2015
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@Profile("dev")
public class DevDatabaseConfig {

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabase(H2);
        adapter.setDatabasePlatform("org.eclipse.persistence.platform.database.H2Platform");
        return adapter;
    }

    @Bean
    public PersistenceUnitManager unitManager() {
        final DefaultPersistenceUnitManager unitManager = new DefaultPersistenceUnitManager();
        unitManager.setPersistenceXmlLocation("classpath:META-INF/dev-persistence.xml");
        return unitManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final JpaVendorAdapter jpaVendorAdapter, final JpaDialect dialect) {
        final LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setJpaDialect(dialect);
        emfb.setPersistenceUnitName("h2-eclipselink");
        return emfb;
    }
}