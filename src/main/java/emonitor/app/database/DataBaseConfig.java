package emonitor.app.database;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
public class DataBaseConfig {

    @Bean
    public JndiObjectFactoryBean dataSource() {
        final JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
        jndiObjectFB.setJndiName("jdbc/chdDataSource");
        jndiObjectFB.setProxyInterface(DataSource.class);
        jndiObjectFB.setResourceRef(true);
        jndiObjectFB.setLookupOnStartup(true);
        return jndiObjectFB;
    }

    @Bean
    public JpaDialect dialect() {
        return new EclipseLinkJpaDialect();
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory factory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }
}
