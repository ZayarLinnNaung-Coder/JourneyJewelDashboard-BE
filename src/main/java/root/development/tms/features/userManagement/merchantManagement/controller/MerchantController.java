package root.development.tms.features.userManagement.merchantManagement.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.userManagement.merchantManagement.domain.request.AddMerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.request.MerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.request.UpdateMerchantRequest;
import root.development.tms.features.userManagement.merchantManagement.domain.response.AddMerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.MerchantDetailsResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.MerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.domain.response.UpdateMerchantResponse;
import root.development.tms.features.userManagement.merchantManagement.service.MerchantService;
import root.development.tms.global.BaseController;
import root.development.tms.global.constants.SuccessCodeConstants;
import root.development.tms.global.document.Hotels;
import root.development.tms.global.document.Packages;
import root.development.tms.global.document.Place;
import root.development.tms.global.document.Transportation;
import root.development.tms.global.domain.CustomResponse;
import root.development.tms.global.repo.MerchantRepo;
import root.development.tms.global.utils.MessageBundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static root.development.tms.global.constants.SuccessCodeConstants.SUC_COM001;

@RestController
@RequestMapping("/api/merchants")
@AllArgsConstructor
public class MerchantController extends BaseController {

    private final MerchantService merchantService;
    private final MerchantRepo merchantRepo;
    private final MongoTemplate mongoTemplate;

    private final MessageBundle messageBundle;

    @PostMapping
    public ResponseEntity<CustomResponse<AddMerchantResponse>> addMerchant(@RequestBody AddMerchantRequest request) {
        AddMerchantResponse response = merchantService.addMerchant(request);
        return createResponse(HttpStatus.CREATED, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @GetMapping
    public ResponseEntity<CustomResponse<Page<MerchantResponse>>> getMerchants(
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

    @GetMapping("/{merchantId}/packages")
    public ResponseEntity<CustomResponse<Page<MerchantPackageResponse>>> getMerchantPackages(
            @PathVariable String merchantId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        try {
            // Create pageable
            Pageable pageable = PageRequest.of(page, size);

            // Query packages by merchantId
            Query packageQuery = new Query(Criteria.where("merchantId").is(merchantId));
            packageQuery.with(pageable);

            List<Packages> packages = mongoTemplate.find(packageQuery, Packages.class);
            long totalPackages = mongoTemplate.count(Query.query(Criteria.where("merchantId").is(merchantId)), Packages.class);

            // Convert to response DTOs with related data
            List<MerchantPackageResponse> packageResponses = new ArrayList<>();

            for (Packages pkg : packages) {
                MerchantPackageResponse response = new MerchantPackageResponse();
                response.setId(pkg.getId());
                response.setName(pkg.getName());
                response.setDescription(pkg.getDescription());
                response.setPrice(pkg.getPrice());
                response.setSelectedRoomType(pkg.getSelectedRoomType());
                response.setSelectedMealPlan(pkg.getSelectedMealPlan());

                // Fetch and set place information
                if (pkg.getPlaceId() != null) {
                    Optional<Place> place = Optional.ofNullable(mongoTemplate.findById(pkg.getPlaceId(), Place.class));
                    if (place.isPresent()) {
                        PlaceInfo placeInfo = new PlaceInfo();
                        placeInfo.setId(place.get().getId());
                        placeInfo.setName(place.get().getName());
                        placeInfo.setPlace(place.get().getPlace());
                        placeInfo.setDescription(place.get().getDescription());
                        placeInfo.setMinBudget(place.get().getMinBudget());
                        placeInfo.setPlaceType(place.get().getPlaceType());
                        placeInfo.setImageUrl(place.get().getImageUrl());
                        response.setPlaceInfo(placeInfo);
                    }
                }

                // Fetch and set hotel information
                if (pkg.getHotelId() != null) {
                    Optional<Hotels> hotel = Optional.ofNullable(mongoTemplate.findById(pkg.getHotelId(), Hotels.class));
                    if (hotel.isPresent()) {
                        HotelInfo hotelInfo = new HotelInfo();
                        hotelInfo.setId(hotel.get().getId());
                        hotelInfo.setName(hotel.get().getName());
                        hotelInfo.setPhoneNumber(hotel.get().getPhoneNumber());
                        hotelInfo.setImageUrl(hotel.get().getImageUrl());
                        hotelInfo.setDescription(hotel.get().getDescription());
                        hotelInfo.setPlaceId(hotel.get().getPlaceId());
                        hotelInfo.setRoomTypes(hotel.get().getRoomTypes());
                        hotelInfo.setMealPlans(hotel.get().getMealPlans());
                        response.setHotelInfo(hotelInfo);
                    }
                }

                // Fetch and set transportation information
                if (pkg.getTransportationId() != null) {
                    Optional<Transportation> transportation = Optional.ofNullable(mongoTemplate.findById(pkg.getTransportationId(), Transportation.class));
                    if (transportation.isPresent()) {
                        TransportationInfo transportationInfo = new TransportationInfo();
                        transportationInfo.setId(transportation.get().getId());
                        transportationInfo.setName(transportation.get().getName());
                        transportationInfo.setDescription(transportation.get().getDescription());
                        transportationInfo.setTimeList(transportation.get().getTimeList());
                        transportationInfo.setPhoneNumber(transportation.get().getPhoneNumber());
                        transportationInfo.setPriceList(transportation.get().getPriceList());
                        response.setTransportationInfo(transportationInfo);
                    }
                }

                packageResponses.add(response);
            }

            // Create paginated response
            Page<MerchantPackageResponse> pageResponse = new PageImpl<>(packageResponses, pageable, totalPackages);

            return createResponse(HttpStatus.OK, pageResponse, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);

        } catch (Exception e) {
            // Log error and return appropriate response
            e.printStackTrace();
            return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, null, "Error fetching merchant packages", "ERR_500");
        }
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

    @Getter
    @Setter
    public static class MerchantPackageResponse {
        private String id;
        private String name;
        private String description;
        private String price;
        private String selectedRoomType;
        private String selectedMealPlan;
        private PlaceInfo placeInfo;
        private HotelInfo hotelInfo;
        private TransportationInfo transportationInfo;
    }

    @Getter
    @Setter
    public static class PlaceInfo {
        private String id;
        private String name;
        private String place;
        private Double minBudget;
        private String description;
        private Object placeType; // Replace with actual PlaceType enum if available
        private String imageUrl;
    }

    @Getter
    @Setter
    public static class HotelInfo {
        private String id;
        private String name;
        private String phoneNumber;
        private String imageUrl;
        private String description;
        private String placeId;
        private List<Hotels.RoomType> roomTypes;
        private List<Hotels.MealPlan> mealPlans;
    }

    @Getter
    @Setter
    public static class TransportationInfo {
        private String id;
        private String name;
        private String description;
        private String timeList;
        private String phoneNumber;
        private List<Transportation.Price> priceList;
    }
}
