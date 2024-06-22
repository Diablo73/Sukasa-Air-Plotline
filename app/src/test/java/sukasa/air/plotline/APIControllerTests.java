package sukasa.air.plotline;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sukasa.air.plotline.controller.APIController;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.service.LoginService;
import sukasa.air.plotline.utils.MapperUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SukasaAirPlotlineApplication.class)
public class APIControllerTests {

	private MockMvc mockMvc;

	@InjectMocks
	private transient APIController apiController;

	@Mock
	private transient LoginService loginService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}


	@Test
	public void defaultMessageTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/default");
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn()
		;
	}

	@Test
	public void loginApiSuccessTest() throws Exception {

		LoginResponse expectedLoginResponse = TestUtil.getLoginServiceResponseBody(false);
		Mockito.when(loginService.initiateLogin(Mockito.any(LoginRequest.class))).thenReturn(expectedLoginResponse);
		RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/login").content(MapperUtil.convertObject2JsonString(TestUtil.getLoginServiceRequestBody(false)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);;
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.is(expectedLoginResponse.getToken())))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
