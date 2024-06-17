package sukasa.air.plotline;

import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.utils.JwtTokenUtil;

public class TestUtil {

	public static LoginRequest getLoginServiceRequestBody(boolean isAdmin) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(isAdmin ? "admin@sukasaair.com" : "asdfg@f5.si");
		return loginRequest;
	}

	public static LoginResponse getLoginServiceResponseBody(boolean isAdmin) {
		LoginResponse loginResponse = new LoginResponse(StatusEnum.SUCCESS);
		loginResponse.setToken(JwtTokenUtil.generateToken(isAdmin ? "admin@sukasaair.com" : "asdfg@f5.si"));
		return loginResponse;
	}
}
