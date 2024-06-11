package sukasa.air.plotline.service.impls;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
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
						assert StringUtils.isNotBlank(loginRequest.getEmail());
					}

					@Override
					public LoginResponse invoke() {
						loginResponse.setToken(JwtTokenUtil.generateToken(loginRequest.getEmail()));
						return loginResponse;

					}

					@Override
					public LoginResponse composeFailResultInfo() {
						return null;
					}
				});
    }
}
