package sukasa.air.plotline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	public static int JWT_TOKEN_EXPIRY_IN_SECONDS = 300;

	public int getJwtTokenExpiryInSeconds() {
		return JWT_TOKEN_EXPIRY_IN_SECONDS;
	}
	@Value("${appConfig.jwtTokenExpiryInSeconds:300}")
	public void setJwtTokenExpiryInSeconds(int jwtTokenExpiryInSeconds) {
		JWT_TOKEN_EXPIRY_IN_SECONDS = jwtTokenExpiryInSeconds;
	}
}
