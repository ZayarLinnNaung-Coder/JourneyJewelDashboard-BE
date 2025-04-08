package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rootstudio.development.dms.global.document.Country;

@Getter
@Setter
@Builder
public class CountryResponse {
    private String id;
    private String name;
    private String countryCode;

    public static CountryResponse of(Country country) {
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .countryCode(country.getCountryCode())
                .build();
    }
    public static CountryResponse from(Document doc) {

        return CountryResponse.builder()
                .id(doc.getObjectId("_id").toString())
                .name(doc.getString("name"))
                .countryCode(doc.getString("countryCode"))
                .build();
    }
}
