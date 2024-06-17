package sukasa.air.plotline.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenUtil {

	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(key)
				.compact();
	}

	public static boolean verifyToken(String token, String subject) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
			return claims.getSubject().equals(subject);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isTokenExpired(String token) {
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();

			Date expiration = claims.getExpiration();
			return expiration != null && expiration.before(new Date());
		} catch (Exception e) {
			return true;
		}
	}

	public static String getSubjectFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
}
