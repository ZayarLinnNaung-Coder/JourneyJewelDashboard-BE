package root.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);

    List<Admin> findByRoleId(String roleId);
}
