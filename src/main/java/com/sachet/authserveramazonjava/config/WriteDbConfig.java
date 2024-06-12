package com.sachet.authserveramazonjava.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EnableJpaRepositories(
        entityManagerFactoryRef = "writeEntityManagerFactory",
        basePackages = {"com.sachet.authserveramazonjava.repository.write"},
        transactionManagerRef = "writeTransactionManager"
)
@EnableTransactionManagement
@Configuration
public class WriteDbConfig {
    private final Environment env;

    public WriteDbConfig(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean(name = "writeDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("write.datasource.url"));
        ds.setDriverClassName(Objects.requireNonNull(env.getProperty("write.datasource.driver-class-name")));
        ds.setUsername(env.getProperty("write.datasource.username"));
        ds.setPassword(env.getProperty("write.datasource.password"));
        return ds;
    }

    @Primary
    @Bean(name = "writeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        bean.setJpaPropertyMap(properties);
        bean.setPackagesToScan("com.sachet.authserveramazonjava.model.write");
        return bean;
    }

    @Primary
    @Bean("writeTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("writeEntityManagerFactory")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
