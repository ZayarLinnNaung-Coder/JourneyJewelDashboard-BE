package rootstudio.development.dms.features.serviceManagement.zoneManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.CityResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.CountryResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.TownshipResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.service.LocationService;
import rootstudio.development.dms.global.BaseController;
import rootstudio.development.dms.global.domain.CustomResponse;
import rootstudio.development.dms.global.utils.MessageBundle;

import static rootstudio.development.dms.global.constants.SuccessCodeConstants.SUC_COM001;

@Validated
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController extends BaseController {

    private final LocationService locationService;
    private final MessageBundle messageBundle;

    @GetMapping("/countries")
    public ResponseEntity<CustomResponse<Page<CountryResponse>>> getAllCountries(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<CountryResponse> response = locationService.getAllCountries(page, size);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @GetMapping("/cities")
    public ResponseEntity<CustomResponse<Page<CityResponse>>> getCitiesByCountry(
            @RequestParam(name = "countryId") String countryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<CityResponse> response = locationService.getCitiesByCountry(countryId, page, size);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }

    @GetMapping("/townships")
    public ResponseEntity<CustomResponse<Page<TownshipResponse>>> getTownshipsByCity(
            @RequestParam(name = "cityId") String cityId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<TownshipResponse> response = locationService.getTownshipsByCity(cityId, page, size);
        return createResponse(
                HttpStatus.OK,
                response,
                messageBundle.getMessage(SUC_COM001),
                SUC_COM001
        );
    }
}