package sukasa.air.plotline.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
	RESET_SUCCESSFUL(00, "RESET_SUCCESSFUL"),
	SEAT_RESERVED(10, "SEAT_RESERVED"),
	SEAT_UNAVAILABLE(11, "SEAT_UNAVAILABLE"),
	INVALID_PARAM(12, "INVALID_PARAM"),

	;

	private final int statusCode;
	private final String message;

	StatusEnum(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
