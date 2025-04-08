package rootstudio.development.tms.features.userManagement.deliveryManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.request.AddDeliveryMenRequest;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.request.DeliveryMenRequest;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.request.UpdateDeliveryMenRequest;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.response.AddDeliveryMenResponse;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.response.DeliveryMenDetailsResponse;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.response.DeliveryMenResponse;
import rootstudio.development.tms.features.userManagement.deliveryManagement.domain.response.UpdateDeliveryMenResponse;
import rootstudio.development.tms.features.userManagement.deliveryManagement.service.DeliveryMenService;
import rootstudio.development.tms.global.BaseController;
import rootstudio.development.tms.global.constants.SuccessCodeConstants;
import static rootstudio.development.tms.global.constants.SuccessCodeConstants.*;
import rootstudio.development.tms.global.domain.CustomResponse;
import rootstudio.development.tms.global.utils.MessageBundle;

@RestController
@RequestMapping("/api/delivery-men")
public class DeliveryMenController extends BaseController {
    @Autowired
    DeliveryMenService deliveryMenService;
    @Autowired
    MessageBundle messageBundle;
    @PostMapping
    public ResponseEntity<CustomResponse<AddDeliveryMenResponse>> addMerchant(@RequestBody AddDeliveryMenRequest request) {
        AddDeliveryMenResponse response = deliveryMenService.addDeliveryMen(request);
        return createResponse(HttpStatus.CREATED, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }
    @GetMapping
    public ResponseEntity<CustomResponse<Page<DeliveryMenResponse>>> searchMerchants(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        DeliveryMenRequest request = new DeliveryMenRequest();
        request.setQuery(query);
        request.setPage(page);
        request.setSize(size);
        Page<DeliveryMenResponse> deliveryMenResponses = deliveryMenService.searchDeliveryMen(request);

        return createResponse(HttpStatus.OK, deliveryMenResponses,messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<DeliveryMenDetailsResponse>> getDeliveryMenById(@PathVariable String id) {
        DeliveryMenDetailsResponse response = deliveryMenService.getDeliveryMenById(id);
        return createResponse(HttpStatus.OK, response, SuccessCodeConstants.SUC_COM001, SUC_COM001);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdateDeliveryMenResponse>> updateDeliveryMen(
            @PathVariable String id,
            @Validated @RequestBody UpdateDeliveryMenRequest request) {
        UpdateDeliveryMenResponse response = deliveryMenService.updateDeliveryMen(id, request);
        return createResponse(HttpStatus.OK, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteDeliveryMen(@PathVariable String id) {
        deliveryMenService.deleteDeliveryMen(id);
        return createResponse(HttpStatus.OK, null, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }
}
