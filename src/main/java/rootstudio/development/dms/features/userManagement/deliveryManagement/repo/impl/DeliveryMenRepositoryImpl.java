package rootstudio.development.dms.features.userManagement.deliveryManagement.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import rootstudio.development.dms.features.userManagement.deliveryManagement.repo.DeliveryMenRepository;
import rootstudio.development.dms.global.document.DeliveryMen;
import rootstudio.development.dms.global.document.Merchant;

import java.util.List;
@Repository
public class DeliveryMenRepositoryImpl implements DeliveryMenRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<DeliveryMen> searchByQuery(String query, Pageable pageable) {
        // Build dynamic query with Criteria
        Criteria criteria = new Criteria();
        if (query != null && !query.isEmpty()) {
            criteria.orOperator(
                    Criteria.where("name").regex(query, "i"),
                    Criteria.where("email").regex(query, "i"),
                    Criteria.where("phoneNumber").regex(query, "i")
            );
        }
        criteria.and("isDeleted").is(false);

        Query mongoQuery = new Query(criteria);

        long totalCount = mongoTemplate.count(mongoQuery, Merchant.class);
        List<DeliveryMen> deliveryMen = mongoTemplate.find(mongoQuery.with(pageable), DeliveryMen.class);

        return new PageImpl<>(deliveryMen, pageable, totalCount);
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return false;
    }
}
