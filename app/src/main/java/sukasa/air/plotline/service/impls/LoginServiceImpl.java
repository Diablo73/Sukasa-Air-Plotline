package sukasa.air.plotline.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sukasa.air.plotline.config.AppConfig;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.exception.InvalidParamException;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
import sukasa.air.plotline.utils.AssertUtil;
import sukasa.air.plotline.utils.JwtTokenUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResponse initiateLogin(LoginRequest loginRequest) {
		final LoginResponse loginResponse = new LoginResponse(StatusEnum.SUCCESS, loginRequest.getEmail());
		return APIProcessTemplateImpl.execute(APIPathConstants.LOGIN,
				new APIProcessTemplate<LoginResponse>() {

					@Override
					public void validate() {
						AssertUtil.notEmpty(loginRequest.getEmail(), "Email cannot be empty");
					}

					@Override
					public LoginResponse invoke() {
						loginResponse.setToken(jwtTokenUtil.generateToken(loginRequest.getEmail(), appConfig.getJwtTokenExpiryInSeconds()));
						return loginResponse;

					}

					@Override
					public LoginResponse composeFailResultInfo(Exception e) {
						InvalidParamException invalidParamException = (InvalidParamException) e;
						loginResponse.setStatusEnum(invalidParamException.getStatusEnum());
						loginResponse.setMessage(invalidParamException.getMessage());
						return loginResponse;
					}
				});
    }
}
