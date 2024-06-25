package sukasa.air.plotline.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import sukasa.air.plotline.config.AppConfig;
import sukasa.air.plotline.config.MongoDbConfig;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.exception.InvalidParamException;
import sukasa.air.plotline.models.mongoDbDocs.UserDoc;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.repository.UserRepository;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
import sukasa.air.plotline.utils.AssertUtil;
import sukasa.air.plotline.utils.HashSukasaUtil;
import sukasa.air.plotline.utils.JwtTokenUtil;

import java.util.Date;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

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
						loginResponse.setToken(generateJwtTokenForLogin(loginRequest.getEmail()));
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

	@Override
	public LoginResponse initiateRegisterAndLogin(LoginRequest loginRequest) {
		final LoginResponse loginResponse = new LoginResponse(StatusEnum.SUCCESS, loginRequest.getEmail());
		return APIProcessTemplateImpl.execute(APIPathConstants.REGISTER,
				new APIProcessTemplate<LoginResponse>() {

					@Override
					public void validate() {
						AssertUtil.notEmpty(loginRequest.getEmail(), "Email cannot be empty");
						AssertUtil.notEmpty(loginRequest.getPassword(), "Password cannot be empty");
					}

					@Override
					public LoginResponse invoke() {
						Optional<UserDoc> userDocFindById = userRepository.findById(loginRequest.getEmail());
						if (userDocFindById.isEmpty()) {
							try {
								UserDoc userDoc = UserDoc.builder()
									.email(loginRequest.getEmail())
									.password(loginRequest.getPassword())
									.hash(HashSukasaUtil.generateEmailPasswordHash(
											loginRequest.getEmail(), loginRequest.getPassword()))
									.isAdmin(MongoDbConfig.getMongoDbAdmin().equals(loginRequest.getEmail()))
									.dateRegistered(new Date())
									.dateLastLoggedIn(new Date())
									.build();
								userRepository.insert(userDoc);
								loginResponse.setStatusEnum(StatusEnum.REGISTERED);
								loginResponse.setToken(generateJwtTokenForLogin(loginRequest.getEmail()));
							} catch (DuplicateKeyException e) {
								loginResponse.setStatusEnum(StatusEnum.FAILED);
							}
						} else {
							if (HashSukasaUtil.isValidPasswordForEmail(loginRequest.getEmail(), loginRequest.getPassword(), userDocFindById.get().getHash())) {
								userDocFindById.get().setDateLastLoggedIn(new Date());
								userRepository.save(userDocFindById.get());
								loginResponse.setStatusEnum(StatusEnum.LOGGED_IN);
								loginResponse.setToken(generateJwtTokenForLogin(loginRequest.getEmail()));
							} else {
								loginResponse.setStatusEnum(StatusEnum.INVALID_PASSWORD);
							}
						}
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

	private String generateJwtTokenForLogin(String email) {
		return jwtTokenUtil.generateToken(email, appConfig.getJwtTokenExpiryInSeconds());
	}
}
