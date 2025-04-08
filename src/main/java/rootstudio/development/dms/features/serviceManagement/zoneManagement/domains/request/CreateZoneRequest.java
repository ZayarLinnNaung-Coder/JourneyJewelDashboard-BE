package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateZoneRequest {
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

