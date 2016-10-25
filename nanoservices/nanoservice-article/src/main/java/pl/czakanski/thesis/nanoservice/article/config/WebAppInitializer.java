package pl.czakanski.thesis.nanoservice.article.config;


import pl.czakanski.thesis.common.config.BaseWebAppInitializer;

public class WebAppInitializer extends BaseWebAppInitializer {

	@Override
	public Class<?> getAppConfigClass() {
		return AppConfig.class;
	}
}