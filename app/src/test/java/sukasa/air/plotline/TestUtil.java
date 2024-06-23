package sukasa.air.plotline;

import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;
import sukasa.air.plotline.models.requests.LoginRequest;
import sukasa.air.plotline.models.requests.ReservationRequest;
import sukasa.air.plotline.models.responses.LoginResponse;
import sukasa.air.plotline.utils.JwtTokenUtil;

public class TestUtil {

	public static LoginRequest getLoginServiceRequestBody(boolean isAdmin) {
		return new LoginRequest(isAdmin ? "admin@sukasaair.com" : "asdfg@f5.si");
	}

	public static LoginResponse getLoginServiceResponseBody(boolean isAdmin) {
		LoginResponse loginResponse = new LoginResponse(StatusEnum.SUCCESS, isAdmin ? "admin@sukasaair.com" : "asdfg@f5.si");
		loginResponse.setToken(JwtTokenUtil.generateTokenStatically(isAdmin ? "admin@sukasaair.com" : "asdfg@f5.si"));
		return loginResponse;
	}


	public static ReservationRequest getSeatModificationServiceRequestBody(int seatNumber) {
		ReservationRequest reservationRequest = new ReservationRequest();
		reservationRequest.setSeatNumber(seatNumber);
		reservationRequest.setPassengerPhone("9876543210");
		reservationRequest.setPassengerName("John");
		reservationRequest.setPassengerAge(30);
		return reservationRequest;
	}

	public static ReservationDoc getReservationDocBody() {
		return ReservationDoc.builder()
				.seatNumber(1)
				.passengerPhone("9876543210")
				.passengerName("John")
				.passengerAge(30)
				.build();
	}
}
