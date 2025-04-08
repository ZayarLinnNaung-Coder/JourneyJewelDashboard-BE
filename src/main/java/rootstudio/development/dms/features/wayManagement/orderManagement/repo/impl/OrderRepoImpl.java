package rootstudio.development.dms.features.wayManagement.orderManagement.repo.impl;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.UpdateOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderDetailsResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderItemResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.repo.OrderRepo;
import rootstudio.development.dms.global.constants.ErrorCodeConstants;
import rootstudio.development.dms.global.document.Order;
import rootstudio.development.dms.global.document.OrderHistory;
import rootstudio.development.dms.global.exception.model.NotFoundCommonException;
import rootstudio.development.dms.global.utils.MessageBundle;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepoImpl implements OrderRepo {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final MessageBundle messageBundle;

    @Override
    public Page<OrderItemResponse> searchOrders(String query, Pageable pageable) {
        Criteria criteria = new Criteria();

        if (query != null && !query.isEmpty()) {
            criteria.orOperator(
                    Criteria.where("senderInfo.senderName").regex(query, "i"),
                    Criteria.where("senderInfo.phoneNumber").regex(query, "i")
            );
        }

       Query mongoQuery = new Query(criteria);
       mongoQuery.with(pageable);
       mongoQuery.fields()
               .include("senderInfo")
               .include("receiverInfo")
               .include("zoneInfo")
               .include("orderCreatedDateTime")
               .include("orderStatus")
               .include("deliveredDateTime")
               .include("deliveryName")

       ;
       long totalCount = mongoTemplate.count(mongoQuery, Order.class);
       List<Order> orders = mongoTemplate.find(mongoQuery, Order.class);

        List<OrderItemResponse> responses = orders.stream()
                .map(order -> OrderItemResponse.of(order, order.getOrderStatus()))
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, totalCount);
    }

    @Override
    public OrderDetailsResponse getOrderById(String orderId) {

        String orderNotFoundMessage = messageBundle.getMessage(ErrorCodeConstants.ERR_COM006);

        Order order = mongoTemplate.findById(orderId, Order.class);
        if (order == null) {
            throw new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, orderNotFoundMessage);
        }

        // implement this code here
        List<OrderHistory> orderHistories = mongoTemplate.find(
                Query.query(Criteria.where("orderId").is(orderId)),
                OrderHistory.class
        );

        return OrderDetailsResponse.of(order, orderHistories);
    }


    @Override
    public Order updateOrder(String orderId, UpdateOrderRequest request) {

        String orderNotFoundMessage = messageBundle.getMessage(ErrorCodeConstants.ERR_COM006);
        Query query = new Query(Criteria.where("_id").is(orderId));

        Update update = new Update()
                .set("senderInfo", request.getSenderInfo())
                .set("receiverInfo", request.getReceiverInfo())
                .set("zoneInfo.fromId", request.getOrderDetails().getDeliveryZone().getFrom().getId())
                .set("zoneInfo.fromName", request.getOrderDetails().getDeliveryZone().getFrom().getName())
                .set("zoneInfo.toId", request.getOrderDetails().getDeliveryZone().getTo().getId())
                .set("zoneInfo.toName", request.getOrderDetails().getDeliveryZone().getTo().getName())
                .set("deliveryFee", request.getOrderDetails().getDeliveryFee())
                .set("codAmount", request.getOrderDetails().getCodAmount());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Order.class);

        if (result.getMatchedCount() == 0) {
            throw new NotFoundCommonException(ErrorCodeConstants.ERR_COM006, orderNotFoundMessage);
        }

        return mongoTemplate.findOne(query, Order.class);
    }
}
