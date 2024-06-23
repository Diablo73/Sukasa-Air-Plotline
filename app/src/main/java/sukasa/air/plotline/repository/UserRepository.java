package sukasa.air.plotline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;
import sukasa.air.plotline.models.mongoDbDocs.UserDoc;

@Repository
public interface UserRepository extends MongoRepository<UserDoc, String> {

}
