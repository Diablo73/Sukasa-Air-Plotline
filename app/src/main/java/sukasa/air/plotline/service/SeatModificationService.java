package sukasa.air.plotline.service;

import sukasa.air.plotline.models.requests.ReservationRequest;
import sukasa.air.plotline.models.responses.ReservationResponse;
import sukasa.air.plotline.models.responses.ResetResponse;

public interface SeatModificationService {
	
	ReservationResponse reserveSeat(ReservationRequest reservationRequest);

	ResetResponse resetSeat();

}
