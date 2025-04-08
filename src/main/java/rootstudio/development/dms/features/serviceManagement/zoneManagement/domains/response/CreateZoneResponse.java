package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.document.Township;
import rootstudio.development.dms.global.document.Zone;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateZoneResponse {
    private String id;
    private String zoneId;
    private String name;
    private String countryId;
    private List<CityResponse> cities;

    public static CreateZoneResponse of(Zone zone) {
        Map<String, List<String>> cityTownships = zone.getTownships() != null ?
                zone.getTownships().stream()
                        .collect(Collectors.groupingBy(
                                Township::getCityId,
                                Collectors.mapping(Township::getId, Collectors.toList())
                        )) :
                Collections.emptyMap();

        return CreateZoneResponse.builder()
                .id(zone.getId())
                .zoneId(zone.getZoneId())
                .name(zone.getName())
                .countryId(zone.getCountry() != null ? zone.getCountry().getId() : null)
                .cities(zone.getCities() != null ?
                        zone.getCities().stream()
                                .map(city -> CityResponse.builder()
                                        .id(city.getId())
                                        .name(city.getName())
                                        .townshipIds(cityTownships.getOrDefault(city.getId(), Collections.emptyList()))
                                        .build())
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CityResponse {
        private String id;
        private String name;
        private List<String> townshipIds;
    }
}
