package sukasa.air.plotline.models.responses;

import lombok.Data;
import sukasa.air.plotline.enums.StatusEnum;

@Data
public class StatusResponse {

	private int statusCode;
	private String message;

	public StatusResponse(StatusEnum statusEnum) {
		statusCode = statusEnum.getStatusCode();
		message = statusEnum.getMessage();
	}

	public void setStatusEnum(StatusEnum statusEnum) {
		statusCode = statusEnum.getStatusCode();
		message = statusEnum.getMessage();
	}
}
