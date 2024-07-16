package aston.bootcamp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("aston.bootcamp")
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
@ComponentScan("aston.bootcamp")
public class DatabaseConfig {
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hibernateDdlAuto;
    @Value("${spring.db.driver.class.name}")
    private String driverName;
    @Value("${spring.db.url}")
    private String databaseURL;
    @Value("${spring.db.usr}")
    private String databaseUsr;
    @Value("${spring.db.pwd}")
    private String databasePwd;
    @Value("${spring.hikari.max-pool-size}")
    private int maxPoolSize;
    @Value("${spring.hikari.min-idle}")
    private int minIdle;
    @Value("${spring.hikari.set-autocommit}")
    private boolean setAutocommit;

    @Bean
    protected DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverName);
        config.setJdbcUrl(databaseURL);
        config.setUsername(databaseUsr);
        config.setPassword(databasePwd);
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

}
