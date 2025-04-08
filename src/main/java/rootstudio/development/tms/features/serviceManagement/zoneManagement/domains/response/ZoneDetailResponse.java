package rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rootstudio.development.tms.global.document.City;
import rootstudio.development.tms.global.document.Township;
import rootstudio.development.tms.global.document.Zone;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@Builder
public class ZoneDetailResponse {
    private String id;
    private String zoneId;
    private String name;
    private CountryResponse country;
    private List<CityResponse> cities;

    public static ZoneDetailResponse of(Zone zone) {
        return ZoneDetailResponse.builder()
                .id(zone.getId())
                .zoneId(zone.getZoneId())
                .name(zone.getName())
                .country(zone.getCountry() != null ? CountryResponse.of(zone.getCountry()) : null)
                .cities(zone.getCities() != null ?
                        zone.getCities().stream()
                                .map(city -> mapCityToResponse(city, zone.getTownships()))
                                .collect(Collectors.toList()) :
                        Collections.<CityResponse>emptyList())
                .build();
    }

    private static CityResponse mapCityToResponse(City city, List<Township> townships) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .townshipList(townships != null ?
                        townships.stream()
                                .filter(t -> t.getCityId().equals(city.getId()))
                                .map(t -> TownshipResponse.builder()
                                        .id(t.getId())
                                        .name(t.getName())
                                        .build())
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }

    public static ZoneDetailResponse from(Document doc) {
        if (doc == null) return null;
        return ZoneDetailResponse.builder()
                .id(doc.getObjectId("id").toString())
                .zoneId(doc.getString("zoneId"))
                .name(doc.getString("name"))
                .country(CountryResponse.from(doc.get("country", Document.class)))
                .cities(doc.getList("cities", Document.class).stream()
                        .map(cityDoc -> CityResponse.builder()
                                .id(cityDoc.getObjectId("id").toString())
                                .name(cityDoc.getString("name"))
                                .townshipList(cityDoc.getList("townshipList", Document.class).stream()
                                        .map(tDoc -> TownshipResponse.builder()
                                                .id(tDoc.getObjectId("_id").toString())
                                                .name(tDoc.getString("name"))
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
