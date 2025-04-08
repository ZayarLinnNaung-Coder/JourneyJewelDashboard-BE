package rootstudio.development.dms.features.userManagement.merchantManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.request.AddMerchantRequest;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.request.MerchantRequest;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.request.UpdateMerchantRequest;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.response.AddMerchantResponse;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.response.MerchantDetailsResponse;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.response.MerchantResponse;
import rootstudio.development.dms.features.userManagement.merchantManagement.domain.response.UpdateMerchantResponse;
import rootstudio.development.dms.features.userManagement.merchantManagement.service.MerchantService;
import rootstudio.development.dms.global.BaseController;
import rootstudio.development.dms.global.constants.SuccessCodeConstants;
import static rootstudio.development.dms.global.constants.SuccessCodeConstants.*;
import rootstudio.development.dms.global.domain.CustomResponse;
import rootstudio.development.dms.global.repo.MerchantRepo;
import rootstudio.development.dms.global.utils.MessageBundle;

@RestController
@RequestMapping("/api/merchants")
@AllArgsConstructor
public class MerchantController extends BaseController {

    private final MerchantService merchantService;
    private final MerchantRepo merchantRepo;

    private final MessageBundle messageBundle;

    @PostMapping
    public ResponseEntity<CustomResponse<AddMerchantResponse>> addMerchant(@RequestBody AddMerchantRequest request) {
        AddMerchantResponse response = merchantService.addMerchant(request);
        return createResponse(HttpStatus.CREATED, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }
    @GetMapping
    public ResponseEntity<CustomResponse<Page<MerchantResponse>>> searchMerchants(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        MerchantRequest request = new MerchantRequest();
        request.setQuery(query);
        request.setPage(page);
        request.setSize(size);
        Page<MerchantResponse> merchantResponses = merchantService.searchMerchants(request);

        return createResponse(HttpStatus.OK, merchantResponses,messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<MerchantDetailsResponse>> getMerchantDetails(
            @PathVariable String id
    ){
        return createResponse(HttpStatus.OK, merchantService.getMerchantDetails(id),messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdateMerchantResponse>> updateMerchant(
            @PathVariable String id,
            @Validated @RequestBody UpdateMerchantRequest request) {
        UpdateMerchantResponse response = merchantService.updateMerchant(id, request);
        return createResponse(HttpStatus.OK, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteMerchant(@PathVariable String id) {
        merchantService.deleteMerchant(id);
        return createResponse(HttpStatus.OK, null, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }
}
