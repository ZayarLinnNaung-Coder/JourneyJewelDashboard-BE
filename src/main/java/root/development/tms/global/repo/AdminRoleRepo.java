package root.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.AdminRole;

public interface AdminRoleRepo extends MongoRepository<AdminRole, String> {
}
