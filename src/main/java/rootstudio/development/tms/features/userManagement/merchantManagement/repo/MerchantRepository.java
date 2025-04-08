package rootstudio.development.tms.features.userManagement.merchantManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rootstudio.development.tms.global.document.Merchant;

public interface MerchantRepository {

    Page<Merchant> searchByQuery(String query, Pageable pageable);
}
