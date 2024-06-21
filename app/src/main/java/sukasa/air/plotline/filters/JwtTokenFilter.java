package sukasa.air.plotline.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import sukasa.air.plotline.utils.JwtTokenUtil;

import java.io.IOException;

@Slf4j
@Component
public class JwtTokenFilter extends BaseFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		log.info(requestURI);
		if (requestURI.contains("seat")) {
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				String token = authorizationHeader.substring(7);
				if (isNotValidToken(response, requestURI, token)) {
					return;
				}
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.getWriter().write("Authorization header missing or invalid");
				response.getWriter().flush();
				return;
			}
		} else {
			log.info("Bypass JWT auth : " + requestURI);
		}
		filterChain.doFilter(request, response);
	}

	private boolean isNotValidToken(HttpServletResponse response, String requestURI, String token) throws IOException {
		if (JwtTokenUtil.isTokenExpired(token)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Invalid or expired token");
			response.getWriter().flush();
			return true;
		}
		if (isAdminCheckFailed(requestURI, token)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Admin access token required");
			response.getWriter().flush();
			return true;
		}
		return false;
	}
}