package root.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.Menu;

import java.util.List;

public interface MenuRepo extends MongoRepository<Menu, String> {

    List<Menu> findByParentMenuIdIsNull();

}
