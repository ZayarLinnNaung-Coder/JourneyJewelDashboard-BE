package rootstudio.development.dms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.dms.global.document.City;
import rootstudio.development.dms.global.document.Country;

import java.util.Optional;

public interface CityRepo extends MongoRepository<City, String> {

    Optional<City> findByName(String name);
}
