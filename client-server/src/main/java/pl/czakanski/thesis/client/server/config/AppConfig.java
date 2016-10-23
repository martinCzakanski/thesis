package pl.czakanski.thesis.client.server.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import pl.czakanski.thesis.common.config.BaseAppConfig;
import pl.czakanski.thesis.common.config.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.List;

@EnableJpaRepositories("pl.czakanski.thesis.common.dao")
@ComponentScan("pl.czakanski.thesis.client.server")
@Import(BaseAppConfig.class)
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAsync
@PropertySource("classpath:/application.properties")
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final Logger LOGGER = Logger.getLogger(AppConfig.class);

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		return BaseAppConfig.createEntityManagerFactory(dataSource(), BaseAppConfig.PERSISTENCE_DEFAULT, env);
	}

	@Bean
	public DataSource dataSource() {
		return new DataSourceBuilder(env)
				.idleConnectionTestPeriodInMinutes("1")
				.idleMaxAgeInMinutes("1")
				.maxConnectionsPerPartition("100")
				.minConnectionsPerPartition("50")
				.partitionCount("4")
				.acquireIncrement("50")
				.statementsCacheSize("100")
				.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
	}

	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}
}