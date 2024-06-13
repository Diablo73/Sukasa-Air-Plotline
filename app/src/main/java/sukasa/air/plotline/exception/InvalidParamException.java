package sukasa.air.plotline.exception;

import lombok.Data;
import sukasa.air.plotline.enums.StatusEnum;

@Data
public class InvalidParamException extends RuntimeException {
	private StatusEnum statusEnum;
	private String message;

	public InvalidParamException(StatusEnum statusEnum, String message) {
		this.statusEnum = statusEnum;
		this.message = message;
	}
}
