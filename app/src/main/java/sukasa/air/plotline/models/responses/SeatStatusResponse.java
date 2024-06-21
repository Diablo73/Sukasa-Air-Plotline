package sukasa.air.plotline.models.responses;

import lombok.Data;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.SeatInfo;

@Data
public class SeatStatusResponse extends StatusResponse {

	private SeatInfo seatInfo;

	public SeatStatusResponse(StatusEnum statusEnum) {
		super(statusEnum);
	}
}
