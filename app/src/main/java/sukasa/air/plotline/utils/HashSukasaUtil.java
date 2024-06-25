package sukasa.air.plotline.utils;

import io.diablo73.common.utils.HashUtil;
import org.apache.commons.lang3.StringUtils;

public class HashSukasaUtil {

	public static String generateEmailPasswordHash(String email, String password) {
		return HashUtil.generateSHA256Hash(HashUtil.generateSHA256Hash(email) + " - " + HashUtil.generateSHA256Hash(password));
	}

	public static boolean isValidPasswordForEmail(String email, String password, String hash) {
		return StringUtils.isNotEmpty(hash) && hash.equals(generateEmailPasswordHash(email, password));
	}
}
