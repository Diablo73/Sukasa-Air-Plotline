package sukasa.air.plotline.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
	RESET_UNSUCCESSFUL("-01", "RESET_UNSUCCESSFUL"),
	RESET_SUCCESSFUL("00", "RESET_SUCCESSFUL"),
	SUCCESS("01", "SUCCESS"),
	SEAT_RESERVED("10", "SEAT_RESERVED"),
	SEAT_UNAVAILABLE("11", "SEAT_UNAVAILABLE"),
	INVALID_PARAM("12", "INVALID_PARAM"),

	;

	private final String statusCode;
	private final String message;

	StatusEnum(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
