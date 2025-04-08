package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZoneRequest {
    private String name;
    private String countryId;
    private List<CityRequest> cities;

    @Getter
    @Setter
    public static class CityRequest {
        private String id;
        private List<String> townshipList;
    }

}