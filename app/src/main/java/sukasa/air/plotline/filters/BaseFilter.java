package sukasa.air.plotline.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sukasa.air.plotline.utils.JwtTokenUtil;

@Component
public abstract class BaseFilter extends OncePerRequestFilter {

	@Value("${mongodb.admin}")
	private String admin;

	public boolean isAdminCheckFailed(String requestURI, String token) {
		return requestURI.contains("reset") && !admin.equals(JwtTokenUtil.getSubjectFromToken(token));
	}
}
