package sukasa.air.plotline;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sukasa.air.plotline.controller.SeatController;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.requests.ReservationRequest;
import sukasa.air.plotline.models.responses.ReservationResponse;
import sukasa.air.plotline.models.responses.ResetResponse;
import sukasa.air.plotline.service.SeatModificationService;
import sukasa.air.plotline.utils.MapperUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Configuration(value = "application-test.yml")
@ContextConfiguration(classes = SukasaAirPlotlineApplication.class)
public class SeatControllerTests extends AbstractTestNGSpringContextTests {

	private MockMvc mockMvc;

	@InjectMocks
	private transient SeatController seatController;

	@Mock
	private transient SeatModificationService seatModificationService;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(seatController).build();
	}


	@Test
	public void defaultMessageTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/seat/");
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn()
		;
	}

	@Test
	public void reserveApiSuccessTest() throws Exception {

		ReservationResponse expectedReservationResponse = new ReservationResponse(StatusEnum.SEAT_RESERVED);
		Mockito.when(seatModificationService.reserveSeat(Mockito.any(ReservationRequest.class))).thenReturn(expectedReservationResponse);
		RequestBuilder request = MockMvcRequestBuilders.post("/seat/reserve").content(MapperUtil.convertObject2JsonString(TestUtil.getSeatModificationServiceRequestBody()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(expectedReservationResponse.getStatusCode())))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void resetApiSuccessTest() throws Exception {

		ResetResponse expectedResetResponse = new ResetResponse(StatusEnum.SEAT_RESERVED);
		Mockito.when(seatModificationService.resetSeat()).thenReturn(expectedResetResponse);
		RequestBuilder request = MockMvcRequestBuilders.get("/seat/reset");
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(expectedResetResponse.getStatusCode())))
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
