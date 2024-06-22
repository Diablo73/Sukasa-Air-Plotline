package sukasa.air.plotline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sukasa.air.plotline.constants.APIPathConstants;
import sukasa.air.plotline.models.requests.ReservationRequest;
import sukasa.air.plotline.models.responses.FetchAllResponse;
import sukasa.air.plotline.models.responses.ReservationResponse;
import sukasa.air.plotline.models.responses.ResetResponse;
import sukasa.air.plotline.models.responses.SeatStatusResponse;
import sukasa.air.plotline.service.SeatModificationService;
import sukasa.air.plotline.service.SeatQueryService;
import sukasa.air.plotline.utils.MapperUtil;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/seat")
public class SeatController {

	@Autowired
	private SeatQueryService seatQueryService;

	@Autowired
	private SeatModificationService seatModificationService;

	@GetMapping({APIPathConstants.DEFAULT})
	public ResponseEntity<String> getDefaultMessage() {
		Date date = new Date();
		return new ResponseEntity<String>(
				"Welcome to the SukasaAirPlotlineApplication!!!" +
						"<br>" +
						"Started Successfully at Time : " + date,
				HttpStatus.OK);
	}

	@GetMapping(value = APIPathConstants.STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> seatStatusController(@PathVariable int seatNumber) {

		SeatStatusResponse seatStatusResponse = seatQueryService.seatStatus(seatNumber);

		return MapperUtil.convertObject2Map(seatStatusResponse);
	}

	@PostMapping(value = APIPathConstants.RESERVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> reserveSeatController(@RequestBody Map<String, Object> requestMap) {

		ReservationRequest reservationRequest = (ReservationRequest) MapperUtil.convertMap2Object(requestMap, ReservationRequest.class);
		ReservationResponse reservationResponse = seatModificationService.reserveSeat(reservationRequest);

		return MapperUtil.convertObject2Map(reservationResponse);
	}

	@GetMapping(value = APIPathConstants.FETCH_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> fetchAllController() {

		FetchAllResponse fetchAllResponse = seatQueryService.fetchAll();

		return MapperUtil.convertObject2Map(fetchAllResponse);
	}

	@GetMapping(value = APIPathConstants.RESET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> resetSeatController() {

		ResetResponse resetResponse = seatModificationService.resetSeat();

		return MapperUtil.convertObject2Map(resetResponse);
	}
}
