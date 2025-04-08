package rootstudio.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.tms.global.document.Country;

import java.util.Optional;

public interface CountryRepo  extends MongoRepository<Country, String> {

    Optional<Country> findByName(String name);
}
