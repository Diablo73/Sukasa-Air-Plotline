package sukasa.air.plotline.models.responses;

import lombok.Data;
import sukasa.air.plotline.enums.StatusEnum;
import sukasa.air.plotline.models.SeatInfo;

import java.util.ArrayList;
import java.util.List;

@Data
public class FetchAllResponse extends StatusResponse {

	private int totalSeatsBooked = 0;
	private List<SeatInfo> seatInfoList = new ArrayList<>();

	public FetchAllResponse(StatusEnum statusEnum) {
		super(statusEnum);
	}

	public void addSeatInfo(SeatInfo seatInfo) {
		seatInfoList.add(seatInfo);
	}

	public void setTotalSeatsBooked() {
		totalSeatsBooked = seatInfoList.size();
	}
}
