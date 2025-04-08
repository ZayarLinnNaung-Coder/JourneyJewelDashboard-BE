package rootstudio.development.dms.global.domain;

import lombok.Data;

import java.util.List;

@Data
public class CountryData {
    private String country;
    private String countryCode;
    private List<CityData> cities;

    @Data
    public static class CityData {
        private String name;
        private List<String> townships;
    }
}