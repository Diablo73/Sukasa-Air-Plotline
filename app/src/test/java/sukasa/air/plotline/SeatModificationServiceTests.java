package sukasa.air.plotline;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.exception.InvalidParamException;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;
import sukasa.air.plotline.models.requests.ReservationRequest;
import sukasa.air.plotline.models.responses.ReservationResponse;
import sukasa.air.plotline.models.responses.ResetResponse;
import sukasa.air.plotline.repository.ReservationRepository;
import sukasa.air.plotline.service.impls.SeatModificationServiceImpl;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Configuration(value = "application-test.yml")
@ContextConfiguration(classes = SukasaAirPlotlineApplication.class)
public class SeatModificationServiceTests {

	private MockMvc mockMvc;

	@InjectMocks
	private SeatModificationServiceImpl seatModificationServiceImpl;

	@Mock
	private ReservationRepository reservationRepository;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(seatModificationServiceImpl).build();
	}


	@Test
	public void seatModificationReserveServiceSuccessTest() {
		ReservationRequest reservationRequest = TestUtil.getSeatModificationServiceRequestBody(1);
		ReservationResponse expectedReservationResponse = new ReservationResponse(StatusEnum.SEAT_RESERVED);
		Mockito.when(reservationRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
		Mockito.when(reservationRepository.insert(Mockito.any(ReservationDoc.class))).thenReturn(TestUtil.getReservationDocBody());
		ReservationResponse actualReservationResponse = seatModificationServiceImpl.reserveSeat(reservationRequest);
		Assert.assertNotNull(actualReservationResponse);
		Assert.assertEquals(expectedReservationResponse.getStatusCode(), actualReservationResponse.getStatusCode());
	}

	@Test
	public void seatModificationReserveServiceDuplicateTest() {
		ReservationRequest reservationRequest = TestUtil.getSeatModificationServiceRequestBody(1);
		ReservationResponse expectedReservationResponse = new ReservationResponse(StatusEnum.SEAT_UNAVAILABLE);
		ReservationDoc reservationDoc = TestUtil.getReservationDocBody();
		Mockito.when(reservationRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(reservationDoc));
		Mockito.when(reservationRepository.insert(Mockito.any(ReservationDoc.class))).thenReturn(reservationDoc);
		ReservationResponse actualReservationResponse = seatModificationServiceImpl.reserveSeat(reservationRequest);
		Assert.assertNotNull(actualReservationResponse);
		Assert.assertEquals(expectedReservationResponse.getStatusCode(), actualReservationResponse.getStatusCode());
	}

	@Test
	public void seatModificationReserveServiceFailureTest() {
		ReservationRequest reservationRequest = TestUtil.getSeatModificationServiceRequestBody(0);
		ReservationResponse expectedReservationResponse = new ReservationResponse(StatusEnum.INVALID_PARAM);
		ReservationResponse actualReservationResponse = seatModificationServiceImpl.reserveSeat(reservationRequest);
		Assert.assertNotNull(actualReservationResponse);
		Assert.assertEquals(expectedReservationResponse.getStatusCode(), actualReservationResponse.getStatusCode());
	}

	@Test
	public void seatModificationResetServiceSuccessTest() {
		ResetResponse expectedResetResponse = new ResetResponse(StatusEnum.RESET_SUCCESSFUL);
		Mockito.doNothing().when(reservationRepository).deleteAll();
		ResetResponse actualResetResponse = seatModificationServiceImpl.resetSeat();
		Assert.assertNotNull(actualResetResponse);
		Assert.assertEquals(expectedResetResponse.getStatusCode(), actualResetResponse.getStatusCode());
	}

	@Test
	public void seatModificationResetServiceFailureTest() {
		ResetResponse expectedResetResponse = new ResetResponse(StatusEnum.RESET_UNSUCCESSFUL);
		Mockito.doThrow(new InvalidParamException(StatusEnum.RESET_UNSUCCESSFUL, "RESET_UNSUCCESSFUL")).when(reservationRepository).deleteAll();
		ResetResponse actualResetResponse = seatModificationServiceImpl.resetSeat();
		Assert.assertNotNull(actualResetResponse);
		Assert.assertEquals(expectedResetResponse.getStatusCode(), actualResetResponse.getStatusCode());
	}
}
