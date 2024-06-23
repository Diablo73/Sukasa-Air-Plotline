package sukasa.air.plotline.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sukasa.air.plotline.config.MongoDbConfig;
import sukasa.air.plotline.utils.JwtTokenUtil;

@Component
public abstract class BaseFilter extends OncePerRequestFilter {

	public boolean isAdminCheckFailed(String requestURI, String token) {
		return requestURI.contains("reset") && !MongoDbConfig.getMongoDbAdmin().equals(JwtTokenUtil.getSubjectFromToken(token));
	}
}
