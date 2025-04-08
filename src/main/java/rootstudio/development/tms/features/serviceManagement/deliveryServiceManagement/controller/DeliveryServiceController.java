package rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.request.AllDeliveryServiceRequest;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.request.CreateDeliveryServiceRequest;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.request.UpdateDeliveryServiceRequest;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.response.AllDeliveryServiceResponse;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.response.CreateDeliveryServiceResponse;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.response.DeliveryServiceDetailsResponse;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.response.UpdateDeliveryServiceResponse;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.service.DeliveryManagementService;
import rootstudio.development.tms.global.BaseController;
import rootstudio.development.tms.global.domain.CustomResponse;
import rootstudio.development.tms.global.utils.MessageBundle;

import static rootstudio.development.tms.global.constants.SuccessCodeConstants.SUC_COM001;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class DeliveryServiceController extends BaseController {
    private final DeliveryManagementService deliveryManagementService;
    private final MessageBundle messageBundle;
    @GetMapping
    public ResponseEntity<CustomResponse<Page<AllDeliveryServiceResponse>>> getAllZones(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        AllDeliveryServiceRequest request = new AllDeliveryServiceRequest();
        request.setQuery(query);
        request.setPage(page);
        request.setSize(size);

        Page<AllDeliveryServiceResponse> deliveryServiceResponses = deliveryManagementService.getAllDeliveryService(request);
        return createResponse(
                HttpStatus.OK,
                deliveryServiceResponses,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<DeliveryServiceDetailsResponse>> getDeliveryServiceById(
            @PathVariable String id) {
        DeliveryServiceDetailsResponse response = deliveryManagementService.getDeliveryServiceById(id);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @PostMapping
    public ResponseEntity<CustomResponse<CreateDeliveryServiceResponse>> createDeliveryService(
            @Validated @RequestBody CreateDeliveryServiceRequest request) {
        CreateDeliveryServiceResponse response = deliveryManagementService.createDeliveryService(request);
        return createResponse(
                HttpStatus.CREATED,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdateDeliveryServiceResponse>> updateDeliveryService(
            @PathVariable String id,
            @Validated @RequestBody UpdateDeliveryServiceRequest request) {
        UpdateDeliveryServiceResponse response = deliveryManagementService.updateDeliveryService(id, request);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }
}
