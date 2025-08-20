package root.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import root.development.tms.global.document.Merchant;

public interface MerchantRepo extends MongoRepository<Merchant, String> {
}
