package root.development.tms.features.hotelManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.Hotels;

public interface HotelRepo extends MongoRepository<Hotels, String> {
    Page<Hotels> findByName(String name, Pageable pageable);
}
