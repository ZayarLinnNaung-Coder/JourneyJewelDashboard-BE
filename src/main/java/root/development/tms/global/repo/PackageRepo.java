package root.development.tms.global.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.Packages;

public interface PackageRepo extends MongoRepository<Packages, String> {

    Page<Packages> findByName(String name, Pageable pageable);

}
