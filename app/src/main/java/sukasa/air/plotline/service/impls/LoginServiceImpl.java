package sukasa.air.plotline.service.impls;

import org.springframework.stereotype.Service;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
import sukasa.air.plotline.utils.AssertUtil;
import sukasa.air.plotline.utils.JwtTokenUtil;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse initiateLogin(LoginRequest loginRequest) {
		final LoginResponse loginResponse = new LoginResponse();
		return APIProcessTemplateImpl.execute(APIPathConstants.LOGIN,
				new APIProcessTemplate<LoginResponse>() {

					@Override
					public void validate() {
						AssertUtil.notEmpty(loginRequest.getEmail(), "Email cannot be empty");
					}

					@Override
					public LoginResponse invoke() {
						loginResponse.setToken(JwtTokenUtil.generateToken(loginRequest.getEmail()));
						return loginResponse;

					}

					@Override
					public LoginResponse composeFailResultInfo(Exception e) {
						return null;
					}
				});
    }
}
