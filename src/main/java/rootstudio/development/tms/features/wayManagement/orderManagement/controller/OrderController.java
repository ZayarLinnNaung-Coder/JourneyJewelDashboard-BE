package rootstudio.development.tms.features.wayManagement.orderManagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.request.CreateOrderRequest;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.request.SearchOrderRequest;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.request.UpdateOrderRequest;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.response.CreateOrderResponse;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.response.OrderDetailsResponse;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.response.OrderItemResponse;
import rootstudio.development.tms.features.wayManagement.orderManagement.domain.response.UpdateOrderResponse;
import rootstudio.development.tms.features.wayManagement.orderManagement.service.OrderService;
import rootstudio.development.tms.global.BaseController;
import rootstudio.development.tms.global.constants.SuccessCodeConstants;
import rootstudio.development.tms.global.domain.CustomResponse;
import rootstudio.development.tms.global.utils.MessageBundle;

import static rootstudio.development.tms.global.constants.SuccessCodeConstants.SUC_COM001;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MessageBundle messageBundle;

    @PostMapping
    public ResponseEntity<CustomResponse<CreateOrderResponse>> createOrder(@RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = orderService.createOrder(request);
        return createResponse(HttpStatus.CREATED, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }
    @GetMapping
    public ResponseEntity<CustomResponse<Page<OrderItemResponse>>> getOrders(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        SearchOrderRequest request = new SearchOrderRequest();
        request.setQuery(query);
        request.setPage(page);
        request.setSize(size);
        Page<OrderItemResponse> orderResponses = orderService.getOrders(request);

        return createResponse(HttpStatus.OK, orderResponses, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<OrderDetailsResponse>> getOrderById(@PathVariable String id) {
        OrderDetailsResponse response = orderService.getOrderById(id);
        return createResponse(HttpStatus.OK, response, SuccessCodeConstants.SUC_COM001, SUC_COM001);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UpdateOrderResponse>> updateOrder(
            @PathVariable String id,
            @Validated @RequestBody UpdateOrderRequest request) {
        UpdateOrderResponse response = orderService.updateOrder(id, request);
        return createResponse(HttpStatus.OK, response, messageBundle.getMessage(SuccessCodeConstants.SUC_COM001), SUC_COM001);
    }
}
