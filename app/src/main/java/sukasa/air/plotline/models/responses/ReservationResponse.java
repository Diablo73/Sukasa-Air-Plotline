package sukasa.air.plotline.models.responses;

import lombok.Data;
import sukasa.air.plotline.enums.StatusEnum;

@Data
public class ReservationResponse extends StatusResponse {

	public ReservationResponse(StatusEnum statusEnum) {
		super(statusEnum);
	}
}
