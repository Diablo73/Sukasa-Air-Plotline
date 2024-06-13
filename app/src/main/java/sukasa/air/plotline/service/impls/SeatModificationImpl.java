package sukasa.air.plotline.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.exception.InvalidParamException;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;
import sukasa.air.plotline.models.requests.ReservationRequest;
import sukasa.air.plotline.models.responses.ReservationResponse;
import sukasa.air.plotline.models.responses.ResetResponse;
import sukasa.air.plotline.repository.ReservationRepository;
import sukasa.air.plotline.service.SeatModificationService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
import sukasa.air.plotline.utils.AssertUtil;

@Service
public class SeatModificationImpl implements SeatModificationService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public ReservationResponse reserveSeat(ReservationRequest reservationRequest) {
		final ReservationResponse reservationResponse = new ReservationResponse(StatusEnum.SEAT_RESERVED);
		return APIProcessTemplateImpl.execute(APIPathConstants.LOGIN,
				new APIProcessTemplate<ReservationResponse>() {

					@Override
					public void validate() {
						AssertUtil.assertTrue(reservationRequest.getSeatNumber() > 0 && reservationRequest.getSeatNumber() <= 300, "seatNumber must be between 1-300");
						AssertUtil.notEmpty(reservationRequest.getPassengerPhone(), "passengerPhone cannot be empty");
						AssertUtil.notEmpty(reservationRequest.getPassengerName(), "passengerName cannot be empty");
						AssertUtil.assertTrue(reservationRequest.getPassengerAge() > 0 && reservationRequest.getPassengerAge() <= 100, "passengerAge must be between 1-100");
					}

					@Override
					public ReservationResponse invoke() {
						ReservationDoc reservationDoc = ReservationDoc.builder()
								.seatNumber(reservationRequest.getSeatNumber())
								.passengerPhone(reservationRequest.getPassengerPhone())
								.passengerName(reservationRequest.getPassengerName())
								.passengerAge(reservationRequest.getPassengerAge())
								.build();

						if (reservationRepository.findById(reservationDoc.getSeatNumber()).isEmpty()) {
							reservationRepository.insert(reservationDoc);
						} else {
							reservationResponse.setStatusEnum(StatusEnum.SEAT_UNAVAILABLE);
						}
						return reservationResponse;
					}

					@Override
					public ReservationResponse composeFailResultInfo(Exception e) {
						InvalidParamException invalidParamException = (InvalidParamException) e;
						reservationResponse.setStatusEnum(invalidParamException.getStatusEnum());
						reservationResponse.setMessage(invalidParamException.getMessage());
						return reservationResponse;
					}
				});
	}

	@Override
	public ResetResponse resetSeat() {
		final ResetResponse resetResponse = new ResetResponse(StatusEnum.RESET_SUCCESSFUL);
		return APIProcessTemplateImpl.execute(APIPathConstants.LOGIN,
				new APIProcessTemplate<ResetResponse>() {

					@Override
					public void validate() {

					}

					@Override
					public ResetResponse invoke() {
						reservationRepository.deleteAll();

						return resetResponse;
					}

					@Override
					public ResetResponse composeFailResultInfo(Exception e) {
						return null;
					}
				});
	}
}
