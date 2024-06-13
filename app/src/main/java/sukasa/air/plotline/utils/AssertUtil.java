package sukasa.air.plotline.utils;

import org.apache.commons.lang3.StringUtils;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.exception.InvalidParamException;

public class AssertUtil {

	public static void notEmpty(String s, String message) {
		if (StringUtils.isEmpty(s)) {
			throw new InvalidParamException(StatusEnum.INVALID_PARAM, message);
		}
	}
}
