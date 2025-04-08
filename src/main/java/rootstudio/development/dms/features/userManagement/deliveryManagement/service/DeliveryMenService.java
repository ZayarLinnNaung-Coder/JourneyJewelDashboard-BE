package rootstudio.development.dms.features.userManagement.deliveryManagement.service;

import org.springframework.data.domain.Page;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.request.AddDeliveryMenRequest;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.request.DeliveryMenRequest;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.request.UpdateDeliveryMenRequest;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.AddDeliveryMenResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.DeliveryMenDetailsResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.DeliveryMenResponse;
import rootstudio.development.dms.features.userManagement.deliveryManagement.domain.response.UpdateDeliveryMenResponse;

public interface DeliveryMenService {

    Page<DeliveryMenResponse> searchDeliveryMen(DeliveryMenRequest request);
    DeliveryMenDetailsResponse getDeliveryMenById(String id);
    AddDeliveryMenResponse addDeliveryMen(AddDeliveryMenRequest request);
    UpdateDeliveryMenResponse updateDeliveryMen(String id, UpdateDeliveryMenRequest request);
    void deleteDeliveryMen(String id);
}
