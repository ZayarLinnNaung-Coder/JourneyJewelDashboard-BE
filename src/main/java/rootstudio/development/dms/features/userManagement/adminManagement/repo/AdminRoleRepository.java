package rootstudio.development.dms.features.userManagement.adminManagement.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import rootstudio.development.dms.global.document.AdminRole;

import java.util.List;

public interface AdminRoleRepository extends MongoRepository<AdminRole, String> {

    @Query("{ 'deleted': false }")
    List<AdminRole> findActiveRoles();

}