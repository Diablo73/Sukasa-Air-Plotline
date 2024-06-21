package sukasa.air.plotline.service.impls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.SeatInfo;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;
import sukasa.air.plotline.models.responses.FetchAllResponse;
import sukasa.air.plotline.models.responses.SeatStatusResponse;
import sukasa.air.plotline.repository.ReservationRepository;
import sukasa.air.plotline.service.SeatQueryService;
import sukasa.air.plotline.template.APIProcessTemplate;
import sukasa.air.plotline.template.APIProcessTemplateImpl;
import sukasa.air.plotline.utils.AssertUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SeatQueryServiceImpl implements SeatQueryService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ReservationRepository reservationRepository;


	@Override
	public SeatStatusResponse seatStatus(int seatNumber) {
		final SeatStatusResponse seatStatusResponse = new SeatStatusResponse(StatusEnum.SEAT_RESERVED);
		return APIProcessTemplateImpl.execute(APIPathConstants.STATUS,
				new APIProcessTemplate<SeatStatusResponse>() {

					@Override
					public void validate() {
						AssertUtil.assertTrue(seatNumber > 0 && seatNumber <= 300, "seatNumber must be between 1-300");
					}

					@Override
					public SeatStatusResponse invoke() {
						Optional<ReservationDoc> findByIdResult = reservationRepository.findById(seatNumber);
						if (findByIdResult.isEmpty()) {
							seatStatusResponse.setStatusEnum(StatusEnum.SEAT_AVAILABLE);
						} else {
							ReservationDoc bookedSeat = findByIdResult.get();
							SeatInfo seatInfo = SeatInfo.builder()
									.seatNumber(bookedSeat.getSeatNumber())
									.passengerPhone(bookedSeat.getPassengerPhone())
									.passengerName(bookedSeat.getPassengerName())
									.passengerAge(bookedSeat.getPassengerAge())
									.build();
							seatStatusResponse.setSeatInfo(seatInfo);
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

	@Override
	public FetchAllResponse fetchAll() {
		final FetchAllResponse fetchAllResponse = new FetchAllResponse(StatusEnum.SUCCESS);
		return APIProcessTemplateImpl.execute(APIPathConstants.FETCH_ALL,
				new APIProcessTemplate<FetchAllResponse>() {

					@Override
					public void validate() {
					}

					@Override
					public FetchAllResponse invoke() {
						List<ReservationDoc> findAllResult = reservationRepository.findAll();

						if (!findAllResult.isEmpty()) {
							for (ReservationDoc reservationDoc : findAllResult) {
								SeatInfo seatInfo = SeatInfo.builder()
										.seatNumber(reservationDoc.getSeatNumber())
										.passengerPhone(reservationDoc.getPassengerPhone())
										.passengerName(reservationDoc.getPassengerName())
										.passengerAge(reservationDoc.getPassengerAge())
										.build();
								fetchAllResponse.addSeatInfo(seatInfo);
							}
						}
						fetchAllResponse.setTotalSeatsBooked();
						return fetchAllResponse;
					}

					@Override
					public FetchAllResponse composeFailResultInfo(Exception e) {
						fetchAllResponse.setStatusEnum(StatusEnum.FAILED);
						fetchAllResponse.setMessage(e.getMessage());
						return fetchAllResponse;
					}
				});
	}

}
