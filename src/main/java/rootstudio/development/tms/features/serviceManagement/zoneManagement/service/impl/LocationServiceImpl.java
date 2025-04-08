package rootstudio.development.tms.features.serviceManagement.zoneManagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.CityResponse;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.CountryResponse;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.TownshipResponse;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.service.LocationService;
import rootstudio.development.tms.global.document.City;
import rootstudio.development.tms.global.document.Country;
import rootstudio.development.tms.global.document.Township;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<CountryResponse> getAllCountries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        long total = mongoTemplate.count(new Query(), Country.class);

        List<AggregationOperation> operations = new ArrayList<>();
        operations.addAll(Arrays.asList(
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "name")),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        ));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        List<Country> countries = mongoTemplate.aggregate(aggregation, "country", Country.class)
                .getMappedResults();

        return new PageImpl<>(
                countries.stream().map(this::mapToCountryResponse).collect(Collectors.toList()),
                pageable,
                total
        );
    }

    private CountryResponse mapToCountryResponse(Country country) {
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .countryCode(country.getCountryCode())
                .build();
    }

    @Override
    public Page<CityResponse> getCitiesByCountry(String countryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Criteria criteria = new Criteria();
        if (StringUtils.hasText(countryId)) {
            criteria.and("countryId").is(countryId);
        }

        long total = mongoTemplate.count(Query.query(criteria), City.class);

        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(criteria));
        operations.addAll(Arrays.asList(
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "name")),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        ));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        List<City> cities = mongoTemplate.aggregate(aggregation, "city", City.class)
                .getMappedResults();

        return new PageImpl<>(
                cities.stream().map(this::mapToCityResponse).collect(Collectors.toList()),
                pageable,
                total
        );
    }

    @Override
    public Page<TownshipResponse> getTownshipsByCity(String cityId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Criteria criteria = new Criteria();
        if (StringUtils.hasText(cityId)) {
            criteria.and("cityId").is(cityId);
        }

        long total = mongoTemplate.count(Query.query(criteria), Township.class);

        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(criteria));
        operations.addAll(Arrays.asList(
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "name")),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        ));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        List<Township> cities = mongoTemplate.aggregate(aggregation, "township", Township.class)
                .getMappedResults();

        return new PageImpl<>(
                cities.stream().map(TownshipResponse::of).collect(Collectors.toList()),
                pageable,
                total
        );
    }

    private CityResponse mapToCityResponse(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
