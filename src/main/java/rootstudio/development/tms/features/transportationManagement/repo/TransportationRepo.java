package rootstudio.development.tms.features.transportationManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.tms.global.document.Place;
import rootstudio.development.tms.global.document.Transportation;

public interface TransportationRepo extends MongoRepository<Transportation, String> {
    Page<Transportation> findByName(String name, Pageable pageable);
}
