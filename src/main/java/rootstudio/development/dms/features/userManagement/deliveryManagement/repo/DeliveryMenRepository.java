package rootstudio.development.dms.features.userManagement.deliveryManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rootstudio.development.dms.global.document.DeliveryMen;

public interface DeliveryMenRepository {
    Page<DeliveryMen> searchByQuery(String query, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

}
