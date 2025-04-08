package rootstudio.development.dms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.dms.global.document.AdminRole;

public interface AdminRoleRepo extends MongoRepository<AdminRole, String> {
}
