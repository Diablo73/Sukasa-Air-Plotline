package sukasa.air.plotline.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sukasa.air.plotline.models.mongoDbDocs.ReservationDoc;

@Repository
public interface ReservationRepository extends MongoRepository<ReservationDoc, Integer> {

}
