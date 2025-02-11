package com.gk.campaign.config.db;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.gk.campaign.repository.postgres",
        entityManagerFactoryRef = "postgresEntityManager",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgreSQLConfig {

    @Bean
    public DataSource postgresDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties postgresDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource postgresDataSource() {
//        return postgresDataSourceProperties()
//                .initializeDataSourceBuilder()
//                .build();
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(DataSource postgresDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresDataSource);
        em.setPackagesToScan("com.gk.campaign.entities.postgres");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager postgresTransactionManager(LocalContainerEntityManagerFactoryBean postgresEntityManager) {
        return new JpaTransactionManager(postgresEntityManager.getObject());
    }
//    @Bean
//    @ConfigurationProperties("spring.datasource")
//    public DataSourceProperties postgresDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    public DataSource postgresDataSource() {
//        return postgresDataSourceProperties()
//                .initializeDataSourceBuilder()
//                .type(DataSource.class)
//                .build();
//    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(postgresDataSource());
//        em.setPackagesToScan("com.gk.campaign.entities.postgres");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", "update");
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//
//    @Bean
//    public JpaTransactionManager postgresTransactionManager() {
//        return new JpaTransactionManager(postgresEntityManagerFactory().getObject());
//    }
}
