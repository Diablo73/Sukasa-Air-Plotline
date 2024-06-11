package sukasa.air.plotline.service;

import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;

public interface LoginService {
	
	LoginResponse initiateLogin(LoginRequest loginRequest);
}
