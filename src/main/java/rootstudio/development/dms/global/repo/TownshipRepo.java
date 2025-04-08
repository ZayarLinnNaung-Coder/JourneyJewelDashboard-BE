package rootstudio.development.dms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.dms.global.document.Township;

import java.util.List;

public interface TownshipRepo extends MongoRepository<Township, String> {
    List<Township> findByCityId(String cityId);
}
