package emonitor.app.database;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.orm.jpa.vendor.Database.POSTGRESQL;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@Profile("prod")
public class ProdDatabaseConfig {

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabase(POSTGRESQL);
        adapter.setDatabasePlatform("org.eclipse.persistence.platform.database.PostgreSQLPlatform");
        return adapter;
    }

    @Bean
    public PersistenceUnitManager unitManager() {
        final DefaultPersistenceUnitManager unitManager = new DefaultPersistenceUnitManager();
        unitManager.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        return unitManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final JpaVendorAdapter jpaVendorAdapter, final JpaDialect dialect) {
        final LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setJpaVendorAdapter(jpaVendorAdapter);
        emfb.setJpaDialect(dialect);
        emfb.setPersistenceUnitName("postgres-eclipselink");
        return emfb;
    }
}
