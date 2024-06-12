package sukasa.air.plotline.models.requests;

import lombok.Data;

@Data
public class ReservationRequest {

    private int seatNumber;
    private String passengerPhone;
    private String passengerName;
    private int passengerAge;
}
