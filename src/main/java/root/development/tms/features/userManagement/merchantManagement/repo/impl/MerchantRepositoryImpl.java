package root.development.tms.features.userManagement.merchantManagement.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import root.development.tms.features.userManagement.merchantManagement.repo.MerchantRepository;
import root.development.tms.global.document.Merchant;

import java.util.List;

@Repository
public class MerchantRepositoryImpl implements MerchantRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Page<Merchant> searchByQuery(String query, Pageable pageable) {
        // Build dynamic query with Criteria
        Criteria criteria = new Criteria();
        if (query != null && !query.isEmpty()) {
            criteria.orOperator(
                    Criteria.where("name").regex(query, "i"),
                    Criteria.where("email").regex(query, "i"),
                    Criteria.where("phoneNumber").regex(query, "i")
            );
        }

        Query mongoQuery = new Query(criteria);

        long totalCount = mongoTemplate.estimatedCount("merchant");
        List<Merchant> merchants = mongoTemplate.find(mongoQuery.with(pageable), Merchant.class);

        return new PageImpl<>(merchants, pageable, totalCount);
    }
}
