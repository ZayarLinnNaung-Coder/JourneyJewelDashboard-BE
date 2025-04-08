package rootstudio.development.dms.features.wayManagement.orderManagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.CreateOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.SearchOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.UpdateOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.CreateOrderResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderDetailsResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderItemResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.UpdateOrderResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.repo.OrderRepo;
import rootstudio.development.dms.features.wayManagement.orderManagement.service.OrderService;
import rootstudio.development.dms.global.document.Order;
import rootstudio.development.dms.global.document.OrderHistory;
import rootstudio.development.dms.global.enumeration.OrderStatus;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
                .senderInfo(Order.SenderInfo.from(request.getSenderInfo()))
                .receiverInfo(Order.ReceiverInfo.from(request.getReceiverInfo()))
                .zoneInfo(Order.ZoneInfo.from(request.getOrderDetails().getDeliveryZone()))
                .deliveryFee(request.getOrderDetails().getDeliveryFee())
                .codAmount(request.getOrderDetails().getCodAmount())
                .orderStatus(OrderStatus.PENDING)
                .orderCreatedDateTime(LocalDateTime.now())
                .build();
        Order savedOrder = mongoTemplate.save(order);

        OrderHistory orderHistory = OrderHistory.builder()
                .orderId(savedOrder.getId())
                .orderStatus(savedOrder.getOrderStatus())
                .build();

        mongoTemplate.save(orderHistory);
        return CreateOrderResponse.of(savedOrder);
    }

    @Override
    public UpdateOrderResponse updateOrder(String orderId, UpdateOrderRequest request) {
        Order updatedOrder = orderRepo.updateOrder(orderId, request);
        return UpdateOrderResponse.of(updatedOrder);
    }

    @Override
    public Page<OrderItemResponse> getOrders(SearchOrderRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return orderRepo.searchOrders(request.getQuery(), pageable);
    }

    @Override
    public OrderDetailsResponse getOrderById(String orderId) {
        return orderRepo.getOrderById(orderId);
    }
}
