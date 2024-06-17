package sukasa.air.plotline;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.service.impls.LoginServiceImpl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Configuration(value = "application-test.yml")
@ContextConfiguration(classes = SukasaAirPlotlineApplication.class)
public class LoginServiceTests {

	private LoginService loginService;

	@Before
	public void setup() {
		loginService = new LoginServiceImpl();
	}


	@Test
	public void loginServiceSuccessTest() {
		LoginRequest loginRequest = TestUtil.getLoginServiceRequestBody(false);
		LoginResponse actualLoginResponse = loginService.initiateLogin(loginRequest);
		Assert.assertNotNull(actualLoginResponse);
		Assert.assertNotNull(actualLoginResponse.getToken());
		Assert.assertFalse(actualLoginResponse.getToken().isEmpty());
	}

	@Test
	public void loginServiceFailureTest() {
		LoginResponse actualLoginResponse = loginService.initiateLogin(new LoginRequest());
		Assert.assertNotNull(actualLoginResponse);
		Assert.assertNull(actualLoginResponse.getToken());
		Assert.assertEquals(StatusEnum.INVALID_PARAM.getStatusCode(), actualLoginResponse.getStatusCode());
	}
}
