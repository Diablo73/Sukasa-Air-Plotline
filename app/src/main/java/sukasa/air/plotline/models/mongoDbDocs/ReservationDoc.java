package sukasa.air.plotline.models.mongoDbDocs;//package sukasa.air.plotline.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "sukasa_air_reservation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDoc {

    @Id
    private int seatNumber;
    private String passengerPhone;
    private String passengerName;
    private int passengerAge;
}
