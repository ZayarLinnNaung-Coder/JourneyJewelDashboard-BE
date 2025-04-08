package rootstudio.development.dms.features.wayManagement.orderManagement.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.UpdateOrderRequest;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderDetailsResponse;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.response.OrderItemResponse;
import rootstudio.development.dms.global.document.Order;

public interface OrderRepo{
    Page<OrderItemResponse> searchOrders(String query, Pageable pageable);
    OrderDetailsResponse getOrderById(String orderId);
    Order updateOrder(String orderId, UpdateOrderRequest request);
}
