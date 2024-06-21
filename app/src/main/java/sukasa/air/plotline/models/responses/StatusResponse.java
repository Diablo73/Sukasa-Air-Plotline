package sukasa.air.plotline.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import sukasa.air.plotline.enums.StatusEnum;

@Data
@NoArgsConstructor
public class StatusResponse {

	private String statusCode;
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
