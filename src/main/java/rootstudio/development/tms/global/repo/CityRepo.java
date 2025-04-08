package rootstudio.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.tms.global.document.City;

import java.util.Optional;

public interface CityRepo extends MongoRepository<City, String> {

    Optional<City> findByName(String name);
}
