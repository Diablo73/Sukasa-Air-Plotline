package sukasa.air.plotline.service;

import sukasa.air.plotline.models.requests.SeatStatusRequest;
import sukasa.air.plotline.models.responses.SeatStatusResponse;

public interface SeatQueryService {

	SeatStatusResponse seatStatus(SeatStatusRequest statusRequest);
}
