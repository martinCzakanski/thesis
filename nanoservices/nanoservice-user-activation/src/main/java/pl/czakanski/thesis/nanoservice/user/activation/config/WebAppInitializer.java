package pl.czakanski.thesis.nanoservice.user.activation.config;


import pl.czakanski.thesis.common.config.BaseWebAppInitializer;

public class WebAppInitializer extends BaseWebAppInitializer {

	@Override
	public Class<?> getAppConfigClass() {
		return AppConfig.class;
	}
}