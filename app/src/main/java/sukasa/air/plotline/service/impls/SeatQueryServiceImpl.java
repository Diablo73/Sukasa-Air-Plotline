package sukasa.air.plotline.service.impls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.SeatInfo;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;
import sukasa.air.plotline.models.requests.SeatStatusRequest;
import sukasa.air.plotline.models.responses.SeatStatusResponse;
import sukasa.air.plotline.repository.ReservationRepository;
import sukasa.air.plotline.service.SeatQueryService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
import sukasa.air.plotline.utils.AssertUtil;

import java.util.Optional;

@Slf4j
@Service
public class SeatQueryServiceImpl implements SeatQueryService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ReservationRepository reservationRepository;


	@Override
	public SeatStatusResponse seatStatus(SeatStatusRequest seatStatusRequest) {
		final SeatStatusResponse seatStatusResponse = new SeatStatusResponse(StatusEnum.SEAT_RESERVED);
		return APIProcessTemplateImpl.execute(APIPathConstants.STATUS,
				new APIProcessTemplate<SeatStatusResponse>() {

					@Override
					public void validate() {
						AssertUtil.assertTrue(seatStatusRequest.getSeatNumber() > 0 && seatStatusRequest.getSeatNumber() <= 300, "seatNumber must be between 1-300");
					}

					@Override
					public SeatStatusResponse invoke() {

						Optional<ReservationDoc> findByIdResult = reservationRepository.findById(seatStatusRequest.getSeatNumber());
						if (findByIdResult.isEmpty()) {
							seatStatusResponse.setStatusEnum(StatusEnum.SEAT_AVAILABLE);
						} else {
							ReservationDoc bookedSeat = findByIdResult.get();
							seatStatusResponse.setSeatInfo(new SeatInfo(bookedSeat.getSeatNumber(), bookedSeat.getPassengerPhone(), bookedSeat.getPassengerName(), bookedSeat.getPassengerAge()));
						}
						return seatStatusResponse;
					}

					@Override
					public SeatStatusResponse composeFailResultInfo(Exception e) {
						seatStatusResponse.setStatusEnum(StatusEnum.FAILED);
						seatStatusResponse.setMessage(e.getMessage());
						return seatStatusResponse;
					}
				});
	}

}
