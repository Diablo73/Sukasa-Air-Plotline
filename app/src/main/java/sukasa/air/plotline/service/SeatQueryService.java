package sukasa.air.plotline.service;

import sukasa.air.plotline.models.responses.FetchAllResponse;
import sukasa.air.plotline.models.responses.SeatStatusResponse;

public interface SeatQueryService {

	SeatStatusResponse seatStatus(int seatNumber);

	FetchAllResponse fetchAll();

}
