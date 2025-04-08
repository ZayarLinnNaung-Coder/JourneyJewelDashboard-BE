package rootstudio.development.dms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.dms.global.document.Country;

import java.util.Optional;

public interface CountryRepo  extends MongoRepository<Country, String> {

    Optional<Country> findByName(String name);
}
