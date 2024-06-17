package sukasa.air.plotline.models.responses;

import lombok.Data;
import sukasa.air.plotline.enums.StatusEnum;

@Data
public class LoginResponse extends StatusResponse {

	private String token;

	public LoginResponse(StatusEnum statusEnum) {
		super(statusEnum);
	}
}
