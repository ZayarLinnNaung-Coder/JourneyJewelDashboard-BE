package rootstudio.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.tms.global.document.Menu;

import java.util.List;

public interface MenuRepo extends MongoRepository<Menu, String> {

    List<Menu> findByParentMenuIdIsNull();

}
