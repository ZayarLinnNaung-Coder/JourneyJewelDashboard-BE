package root.development.tms.features.placeManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.Place;

public interface PlaceRepo extends MongoRepository<Place, String> {
    Page<Place> findByName(String name, Pageable pageable);
}
