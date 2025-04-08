package rootstudio.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.tms.global.document.AdminRole;

public interface AdminRoleRepo extends MongoRepository<AdminRole, String> {
}
