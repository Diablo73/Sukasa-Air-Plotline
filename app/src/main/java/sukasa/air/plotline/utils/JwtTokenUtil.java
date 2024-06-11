package sukasa.air.plotline.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenUtil {

	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static String generateToken(String subject) {
		return Jwts.builder()
				.setSubject(subject)
				.setExpiration(new Date(System.currentTimeMillis() + 900000)) // 1 hour expiry
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

	public static String getSubjectFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
}
