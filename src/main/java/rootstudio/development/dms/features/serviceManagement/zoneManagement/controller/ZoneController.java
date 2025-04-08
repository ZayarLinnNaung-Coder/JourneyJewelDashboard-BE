package rootstudio.development.dms.features.serviceManagement.zoneManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.AllZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.CreateZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.ZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.CreateZoneResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.UpdateZoneResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneDetailResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.service.ZoneService;
import rootstudio.development.dms.global.BaseController;
import rootstudio.development.dms.global.domain.CustomResponse;
import rootstudio.development.dms.global.utils.MessageBundle;

import static rootstudio.development.dms.global.constants.SuccessCodeConstants.SUC_COM001;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController extends BaseController {

    private final ZoneService zoneService;
    private final MessageBundle messageBundle;
    @PostMapping
    public ResponseEntity<CustomResponse<CreateZoneResponse>> createZone(
            @Validated @RequestBody CreateZoneRequest request) {
        CreateZoneResponse response = zoneService.createZone(request);
        return createResponse(
                HttpStatus.CREATED,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @GetMapping
    public ResponseEntity<CustomResponse<Page<ZoneResponse>>> getAllZones(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        AllZoneRequest request = new AllZoneRequest();
        request.setQuery(query);
        request.setPage(page);
        request.setSize(size);

        Page<ZoneResponse> zoneResponses = zoneService.getAllZones(request);
        return createResponse(
                HttpStatus.OK,
                zoneResponses,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<ZoneDetailResponse>> getZoneById(
            @PathVariable String id) {
        ZoneDetailResponse response = zoneService.getZoneById(id);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdateZoneResponse>> updateZone(
            @PathVariable String id,
            @Validated @RequestBody ZoneRequest request) {
        UpdateZoneResponse response = zoneService.updateZone(id, request);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }
}
