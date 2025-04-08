package rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.City;
import rootstudio.development.tms.global.document.Zone;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@Builder
public class UpdateZoneResponse {
    private String id;
    private String name;
    private String countryId;
    private List<String> cityIds;

    public static UpdateZoneResponse of(Zone zone) {
        return UpdateZoneResponse.builder()
                        .id(zone.getId())
                        .name(zone.getName())
                        .countryId(zone.getCountry() != null ? zone.getCountry().getId() : null)
                        .cityIds(zone.getCities() != null ?
                                zone.getCities().stream()
                                        .map(City::getId)
                                        .collect(Collectors.toList()) :
                                Collections.emptyList())
                .build();
    }
}
