package rootstudio.development.dms.features.serviceManagement.zoneManagement.service;

import org.springframework.data.domain.Page;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.CityResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.CountryResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.TownshipResponse;

public interface LocationService {
    Page<CountryResponse> getAllCountries(int page, int size);
    Page<CityResponse> getCitiesByCountry(String countryId, int page, int size);
    Page<TownshipResponse> getTownshipsByCity(String cityId, int page, int size);
}
