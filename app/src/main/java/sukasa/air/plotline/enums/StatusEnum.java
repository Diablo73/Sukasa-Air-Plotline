package sukasa.air.plotline.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

	SEAT_RESERVED(10, "SEAT_RESERVED"),
	SEAT_UNAVAILABLE(11, "SEAT_UNAVAILABLE"),
	RESET_SUCCESSFUL(12, "RESET_SUCCESSFUL"),

	;

	private final int statusCode;
	private final String message;

	StatusEnum(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
