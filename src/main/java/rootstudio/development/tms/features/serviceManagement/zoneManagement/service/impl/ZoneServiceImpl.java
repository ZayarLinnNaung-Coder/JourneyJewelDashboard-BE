package rootstudio.development.tms.features.serviceManagement.zoneManagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.CreateZoneResponse;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.UpdateZoneResponse;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.ZoneDetailResponse;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.response.ZoneResponse;
import rootstudio.development.tms.global.document.*;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.request.AllZoneRequest;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.request.CreateZoneRequest;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.domains.request.ZoneRequest;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.repo.CustomZoneRepo;
import rootstudio.development.tms.features.serviceManagement.zoneManagement.service.ZoneService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final MongoTemplate mongoTemplate;
    private final CustomZoneRepo zoneRepo;

    @Transactional
    @Override
    public CreateZoneResponse createZone(CreateZoneRequest request) {
        Country country = mongoTemplate.findById(request.getCountryId(), Country.class);
        String zoneId = generateZoneId(country.getCountryCode());

        Zone zone = Zone.builder()
                .name(request.getName())
                .zoneId(zoneId)
                .country(country)
                .build();

        if (request.getCities() != null) {
            List<City> cities = new ArrayList<>();
            List<Township> townships = new ArrayList<>();

            for (CreateZoneRequest.CityRequest cityReq : request.getCities()) {
                City city = mongoTemplate.findById(cityReq.getId(), City.class);
                cities.add(city);

                if (cityReq.getTownshipList() != null && !cityReq.getTownshipList().isEmpty()) {
                    List<Township> cityTownships = mongoTemplate.find(
                            Query.query(Criteria.where("id").in(cityReq.getTownshipList())),
                            Township.class
                    );
                    townships.addAll(cityTownships);
                }
            }

            zone.setCities(cities);
            zone.setTownships(townships);
        }

        Zone savedZone = mongoTemplate.save(zone);
        return CreateZoneResponse.of(savedZone);
    }

    private String generateZoneId(String countryCode) {
        Counter counter = mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is("SQ_"  + countryCode)),
                new Update().inc("seq", 1),
                FindAndModifyOptions.options()
                        .returnNew(true)
                        .upsert(true),
                Counter.class
        );

        if (counter == null) {
            throw new IllegalStateException("Failed to generate zone ID");
        }
        return String.format("Z0%s%04d",
                countryCode.toUpperCase(),
                counter.getSeq());
    }

    @Override
    public Page<ZoneResponse> getAllZones(AllZoneRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return zoneRepo.getAllZones(request.getQuery(), pageable);
    }
    @Override
    public ZoneDetailResponse getZoneById(String id) {
        return zoneRepo.getZoneById(id);
    }

    @Override
    public UpdateZoneResponse updateZone(String id, ZoneRequest request) {
        Zone updateZone = zoneRepo.updateZone(id, request);
        return UpdateZoneResponse.of(updateZone);
    }
}
