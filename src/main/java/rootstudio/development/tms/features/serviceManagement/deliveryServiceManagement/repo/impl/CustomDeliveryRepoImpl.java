package rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.repo.impl;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.request.UpdateDeliveryServiceRequest;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.domains.response.AllDeliveryServiceResponse;
import rootstudio.development.tms.features.serviceManagement.deliveryServiceManagement.repo.CustomDeliveryRepo;
import rootstudio.development.tms.global.document.DeliveryService;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomDeliveryRepoImpl implements CustomDeliveryRepo {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<AllDeliveryServiceResponse> getAllDeliveryService(String query, Pageable pageable) {
        Criteria criteria = new Criteria();
        if (query != null && !query.isEmpty()) {
            criteria.orOperator(
                    Criteria.where("name").regex(query, "i"),
                    Criteria.where("serviceId").regex(query, "i")
            );
        }

        long total = mongoTemplate.count(Query.query(criteria), DeliveryService.class);

        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(Aggregation.match(criteria));
        operations.add(Aggregation.project()
                        .and("$_id").as("id")
                        .and("$serviceId").as("serviceId")
                .and("$name").as("name")
                .and("$price").as("price")
                .and("$description").as("description")
                .and("$iconUrl").as("iconUrl")
        );

        if (pageable.getSort().isSorted()) {
            operations.add(Aggregation.sort(pageable.getSort()));
        } else {
            operations.add(Aggregation.sort(Sort.by("name").ascending()));
        }

        operations.add(Aggregation.skip(pageable.getOffset()));
        operations.add(Aggregation.limit(pageable.getPageSize()));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        List<AllDeliveryServiceResponse> results = mongoTemplate.aggregate(
                aggregation,
                "deliveryService",
                AllDeliveryServiceResponse.class
        ).getMappedResults();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public DeliveryService getDeliveryServiceById(String id) {
        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(Aggregation.match(Criteria.where("_id").is(new ObjectId(id))));
        operations.add(Aggregation.lookup("zone", "fromZone", "_id", "fromZoneData"));
        operations.add(Aggregation.lookup("zone", "toZone", "_id", "toZoneData"));
        operations.add(Aggregation.unwind("$fromZoneData", true));
        operations.add(Aggregation.unwind("$toZoneData", true));

        operations.add(Aggregation.project()
                .and("$_id").as("id")
                .and("$serviceId").as("serviceId")
                .and("$name").as("name")
                .and("$description").as("description")
                .and("$price").as("price")
                .and("$iconUrl").as("iconUrl")
                        .and("$fromZone").as("fromZone")
                        .and("$toZone").as("toZone")
        );

        Aggregation aggregation = Aggregation.newAggregation(operations);

        return mongoTemplate.aggregate(
                aggregation,
                "deliveryService",
                DeliveryService.class
        ).getUniqueMappedResult();
    }

    @Override
    public DeliveryService updateDeliveryService(String id, UpdateDeliveryServiceRequest request) {
        Update update = new Update()
                .set("name", request.getName())
                .set("fromZoneId", request.getFromZoneId() != null ?
                        new ObjectId(request.getFromZoneId()) : null)
                .set("toZoneId", request.getToZoneId() != null ?
                        new ObjectId(request.getToZoneId()) : null)
                .set("price", request.getPrice())
                .set("description", request.getDescription())
                .set("iconUrl", request.getIconUrl());

        return mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(new ObjectId(id))),
                update,
                new FindAndModifyOptions().returnNew(true),
                DeliveryService.class
        );
    }
}
