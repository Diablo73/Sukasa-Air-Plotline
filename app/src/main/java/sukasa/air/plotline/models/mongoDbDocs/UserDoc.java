package sukasa.air.plotline.models.mongoDbDocs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "sukasa_air_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDoc {

    @Id
    private final String email;
    private final String password;
    private final String hash;
    private boolean isAdmin;
    private final Date dateRegistered;
    private Date dateLastLoggedIn;
}
