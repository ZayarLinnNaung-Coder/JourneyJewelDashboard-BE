package rootstudio.development.dms.features.serviceManagement.zoneManagement.repo.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request.ZoneRequest;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneDetailResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.response.ZoneResponse;
import rootstudio.development.dms.features.serviceManagement.zoneManagement.repo.CustomZoneRepo;
import rootstudio.development.dms.global.document.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomZoneRepoImpl implements CustomZoneRepo {
    private final MongoTemplate mongoTemplate;
    @Override
    public Page<ZoneResponse> getAllZones(String query, Pageable pageable) {
        Criteria criteria = new Criteria();
        if (query != null && !query.isEmpty()) {
            criteria.orOperator(
                    Criteria.where("name").regex(query, "i"),
                    Criteria.where("zoneId").regex(query, "i")
            );
        }

        long total = mongoTemplate.count(Query.query(criteria), Zone.class);

        AggregationOperation[] operations = new AggregationOperation[] {
                Aggregation.match(criteria),
                Aggregation.lookup("country", "country", "_id", "countryData"),
                Aggregation.unwind("countryData", true),
                Aggregation.lookup("city", "cities", "_id", "cityData"),
                Aggregation.lookup("township", "townships", "_id", "townshipData"),
                Aggregation.project()
                        .and("$_id").as("id")
                        .and("$zoneId").as("zoneId")
                        .and("$name").as("name")
                        .and("$countryData").as("country")
                        .and(
                                AggregationOperationContext -> new Document("$map",
                                        new Document("input", "$cityData")
                                                .append("as", "city")
                                                .append("in", new Document()
                                                        .append("_id", "$$city._id")
                                                        .append("name", "$$city.name")
                                                        .append("townshipList",
                                                                new Document("$filter",
                                                                        new Document("input", "$townshipData")
                                                                                .append("as", "township")
                                                                                .append("cond", new Document("$eq",
                                                                                        Arrays.asList("$$township.id", "$$township._name")
                                                                                ))
                                                                )
                                                        )
                                                )
                                )
                        ).as("cities")
        };

        List<AggregationOperation> allOperations = new ArrayList<>(Arrays.asList(operations));

        if (pageable.getSort().isSorted()) {
            allOperations.add(Aggregation.sort(pageable.getSort()));
        } else {
            allOperations.add(Aggregation.sort(Sort.by("name").ascending()));
        }

        allOperations.add(Aggregation.skip(pageable.getOffset()));
        allOperations.add(Aggregation.limit(pageable.getPageSize()));

        Aggregation aggregation = Aggregation.newAggregation(allOperations);
        List<ZoneResponse> results = mongoTemplate.aggregate(
                aggregation,
                "zone",
                ZoneResponse.class
        ).getMappedResults();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public ZoneDetailResponse getZoneById(String id) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(new ObjectId(id))),
                Aggregation.lookup("country", "country", "_id", "countryData"),
                Aggregation.unwind("countryData", true),
                Aggregation.lookup("city", "cities", "_id", "cityData"),
                Aggregation.lookup("township", "townships", "_id", "townshipData"),
                Aggregation.project()
                        .and("$_id").as("id")
                        .and("$zoneId").as("zoneId")
                        .and("$name").as("name")
                        .and("$countryData").as("country")
                        .and(
                                AggregationOperationContext -> new Document("$map",
                                        new Document("input", "$cityData")
                                                .append("as", "city")
                                                .append("in", new Document()
                                                        .append("id", "$$city._id")
                                                        .append("name", "$$city.name")
                                                        .append("townshipList",
                                                                new Document("$filter",
                                                                        new Document("input", "$townshipData")
                                                                                .append("as", "township")
                                                                                .append("cond", new Document("$eq",
                                                                                        Arrays.asList("$$township.id", "$$township._name")
                                                                                ))
                                                                )
                                                        )
                                                )
                                )
                        ).as("cities")
        );

        Document resultDoc = mongoTemplate.aggregate(
                aggregation,
                "zone",
                Document.class
        ).getUniqueMappedResult();

        return ZoneDetailResponse.from(resultDoc);
    }

    @Transactional
    @Override
    public Zone updateZone(String id, ZoneRequest request) {
        Zone existingZone = mongoTemplate.findById(id, Zone.class);
        Update update = new Update();

        if (request.getName() != null) {
            update.set("name", request.getName());
        }

        if (request.getCountryId() != null) {
            Country country = mongoTemplate.findById(request.getCountryId(), Country.class);
            update.set("country", country);

            if (!existingZone.getCountry().getId().equals(request.getCountryId())) {
                String newZoneId = generateZoneId(country.getCountryCode());
                update.set("zoneId", newZoneId);
            }
        }

        if (request.getCities() != null) {
            List<ObjectId> cityIds = request.getCities().stream()
                    .filter(c -> c.getId() != null)
                    .map(c -> new ObjectId(c.getId()))
                    .collect(Collectors.toList());

            List<ObjectId> townshipIds = request.getCities().stream()
                    .filter(c -> c.getTownshipList() != null)
                    .flatMap(c -> c.getTownshipList().stream())
                    .map(ObjectId::new)
                    .collect(Collectors.toList());

            update.set("cities", cityIds);
            update.set("townships", townshipIds);
        }

        Zone updatedZone = mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(id)),
                update,
                FindAndModifyOptions.options().returnNew(true),
                Zone.class
        );

        return updatedZone;
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
}
