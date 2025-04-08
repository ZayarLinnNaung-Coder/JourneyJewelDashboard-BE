package rootstudio.development.dms.features.wayManagement.orderManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import rootstudio.development.dms.global.document.DeliveryMen;
import rootstudio.development.dms.global.document.Order;
import rootstudio.development.dms.global.enumeration.OrderStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderItemResponse {

    private String id;
    private String senderName;
    private LocalDateTime orderCreatedDate;
    private OrderStatus orderStatus;
    private LocalDateTime deliveredDate;
    private DeliveryMen deliveryName;

    public static OrderItemResponse of(Order order, OrderStatus orderStatus) {
        return OrderItemResponse.builder()
                .id(order.getId())
                .senderName(order.getSenderInfo().getSenderName())
                .orderCreatedDate(order.getOrderCreatedDateTime())
                .orderStatus(orderStatus)
                .deliveredDate(order.getDeliveredDateTime())
                .deliveryName(order.getPicker())
                .build();
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class OrderStatusHistory {
        private String status;
        private Instant timestamp;

        public static OrderStatusHistory of(OrderStatus orderStatus) {
            return OrderStatusHistory.builder()
                    .status(orderStatus.name())
                    .timestamp(Instant.now())
                    .build();
        }
    }

    public static PaginatedOrderResponse generateResponse(Page<Order> orderPage) {
        return PaginatedOrderResponse.builder()
                .content(orderPage.getContent().stream()
                        .map(order -> OrderItemResponse.of(order, order.getOrderStatus()))
                        .collect(Collectors.toList()))
                .totalPages(orderPage.getTotalPages())
                .totalElements(orderPage.getTotalElements())
                .pageable(PaginatedOrderResponse.Pageable.of(orderPage))
                .first(orderPage.isFirst())
                .last(orderPage.isLast())
                .size(orderPage.getSize())
                .number(orderPage.getNumber())
                .numberOfElements(orderPage.getNumberOfElements())
                .empty(orderPage.isEmpty())
                .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class PaginatedOrderResponse {
        private List<OrderItemResponse> content;
        private Pageable pageable;
        private int totalPages;
        private long totalElements;
        private boolean last;
        private int size;
        private int number;
        private Sort sort;
        private int numberOfElements;
        private boolean first;
        private boolean empty;

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        public static class Pageable {
            private int pageNumber;
            private int pageSize;
            private Sort sort;
            private int offset;
            private boolean unpaged;
            private boolean paged;

            public static Pageable of(Page<?> page) {
                return Pageable.builder()
                        .pageNumber(page.getNumber())
                        .pageSize(page.getSize())
                        .sort(Sort.of(page.getSort()))
                        .offset((int) page.getPageable().getOffset())
                        .unpaged(!page.getPageable().isPaged())
                        .paged(page.getPageable().isPaged())
                        .build();
            }
        }

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        public static class Sort {
            private boolean empty;
            private boolean unsorted;
            private boolean sorted;

            public static Sort of(org.springframework.data.domain.Sort sort) {
                return Sort.builder()
                        .empty(sort.isEmpty())
                        .unsorted(sort.isUnsorted())
                        .sorted(sort.isSorted())
                        .build();
            }
        }
    }
}
