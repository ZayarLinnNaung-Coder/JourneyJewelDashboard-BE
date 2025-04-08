package rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.request.UpdateDeliveryServiceRequest;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.response.AllDeliveryServiceResponse;
import rootstudio.development.tms.global.document.DeliveryService;

public interface CustomDeliveryRepo {
    Page<AllDeliveryServiceResponse> getAllDeliveryService(String query, Pageable pageable);
    DeliveryService getDeliveryServiceById(String id);
    DeliveryService updateDeliveryService(String id, UpdateDeliveryServiceRequest request);
}
