package pl.czakanski.thesis.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BaseAppConfig {

    public static final String PERSISTENCE_DEFAULT = "defaultPersistence";
    public static final String TRANSACTION_DEFAULT = "defaultTransactionManager";

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    public static LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, String persistenceUnitName, Environment env) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("pl.czakanski.thesis.common.model");
        em.setPersistenceUnitName(persistenceUnitName);

        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql("true".equalsIgnoreCase(env.getProperty("jpa.showSql")));
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(getJpaProperties(env));

        return em;
    }

    private static Properties getJpaProperties(Environment env) {
        Properties properties = new Properties();
        properties.setProperty("eclipselink.weaving", "static");
        properties.setProperty("eclipselink.logging.parameters", "true");
        properties.setProperty("eclipselink.cache.type.default", "none");
        properties.setProperty("eclipselink.query-results-cache", "true");
        properties.setProperty("eclipselink.logging.level", env.getProperty("jpa.logging.level", "WARNING"));
        return properties;
    }
}
