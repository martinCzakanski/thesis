package pl.czakanski.thesis.microservice.user.config;


import pl.czakanski.thesis.common.config.BaseWebAppInitializer;

public class WebAppInitializer extends BaseWebAppInitializer {

	@Override
	public Class<?> getAppConfigClass() {
		return AppConfig.class;
	}
}