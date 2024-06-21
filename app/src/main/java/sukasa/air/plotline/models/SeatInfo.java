package sukasa.air.plotline.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatInfo {

	private int seatNumber;
	private String passengerPhone;
	private String passengerName;
	private int passengerAge;
}
