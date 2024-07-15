package aston.bootcamp.config.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ExtendWith(SpringExtension.class)
@EnableJpaRepositories("aston.bootcamp")
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@ComponentScan(basePackages = {"aston.bootcamp.repository", "aston.bootcamp.model", "aston.bootcamp.service", "aston.bootcamp.controllers", "aston.bootcamp.dto"})
public class RepositoryTestConfig {
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;
    @Value("${spring.hikari.max-pool-size}")
    private int maxPoolSize;
    @Value("${spring.hikari.min-idle}")
    private int minIdle;
    @Value("${spring.hikari.set-autocommit}")
    private boolean setAutocommit;

    private PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("bikes_with_spring_test_db")
            .withUsername("postgres")
            .withPassword("postgres");

    @Bean
    protected DataSource dataSource() {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(postgreSQLContainer.getDriverClassName());
        config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
        config.setUsername(postgreSQLContainer.getUsername());
        config.setPassword(postgreSQLContainer.getPassword());
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setAutoCommit(setAutocommit);
        return new HikariDataSource(config);
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("aston.bootcamp");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        properties.setProperty("hibernate.dialect", hibernateDialect);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public JdbcDatabaseDelegate jdbcDatabaseDelegate() {
        return new JdbcDatabaseDelegate(postgreSQLContainer, "");
    }
}
