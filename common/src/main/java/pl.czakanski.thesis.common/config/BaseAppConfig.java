package pl.czakanski.thesis.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class BaseAppConfig {

    public static final String PERSISTENCE_DEFAULT = "defaultPersistence";

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost("smtp.gmail.com");
        javaMailSenderImpl.setPort(587);
        javaMailSenderImpl.setUsername("kontotestowe20160901");
        javaMailSenderImpl.setPassword("SqlYvfDkUY!");

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);

        javaMailSenderImpl.setJavaMailProperties(properties);

        return javaMailSenderImpl;
    }

    public static LocalContainerEntityManagerFactoryBean createEntityManagerFactory(DataSource dataSource, String persistenceUnitName, Environment env) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("pl.czakanski.thesis.common");
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
