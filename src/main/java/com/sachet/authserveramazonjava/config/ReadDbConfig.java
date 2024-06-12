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
        entityManagerFactoryRef = "readEntityManagerFactory",
        basePackages = {"com.sachet.authserveramazonjava.repository.read"},
        transactionManagerRef = "readTransactionManager"
)
@EnableTransactionManagement
@Configuration
public class ReadDbConfig {
    private final Environment env;

    public ReadDbConfig(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean(name = "readDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("read.datasource.url"));
        ds.setDriverClassName(Objects.requireNonNull(env.getProperty("read.datasource.driver-class-name")));
        ds.setUsername(env.getProperty("read.datasource.username"));
        ds.setPassword(env.getProperty("read.datasource.password"));
        return ds;
    }

    @Primary
    @Bean(name = "readEntityManagerFactory")
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
    @Bean("readTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("readEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
