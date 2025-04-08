package rootstudio.development.tms.global.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import rootstudio.development.tms.global.document.Merchant;

public interface MerchantRepo extends MongoRepository<Merchant, String> {
}
