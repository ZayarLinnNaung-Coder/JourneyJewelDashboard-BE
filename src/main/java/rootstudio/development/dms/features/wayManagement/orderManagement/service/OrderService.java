package rootstudio.development.dms.features.wayManagement.orderManagement.service;

import org.springframework.data.domain.Page;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.CreateOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.SearchOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.UpdateOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.CreateOrderResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderDetailsResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderItemResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.UpdateOrderResponse;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    UpdateOrderResponse updateOrder(String orderId, UpdateOrderRequest request);
    Page<OrderItemResponse> getOrders(SearchOrderRequest request);
    OrderDetailsResponse getOrderById(String orderId);
}
