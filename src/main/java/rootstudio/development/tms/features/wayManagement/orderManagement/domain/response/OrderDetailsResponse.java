package rootstudio.development.tms.features.wayManagement.orderManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.DeliveryMen;
import rootstudio.development.tms.global.document.Order;
import rootstudio.development.tms.global.document.OrderHistory;
import rootstudio.development.tms.global.enumeration.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrderDetailsResponse {

    private String id;
    private List<OrderStatusHistory> orderStatus;
    private OrderDetails orderDetails;
    private SenderInfo senderInfo;
    private ReceiverInfo receiverInfo;

    public static OrderDetailsResponse of(Order order, List<OrderHistory> orderHistories) {

        List<OrderStatusHistory> orderStatus = orderHistories.stream().map(OrderStatusHistory::of).toList();

        return OrderDetailsResponse.builder()
                .id(order.getId())
                .senderInfo(SenderInfo.of(order.getSenderInfo()))
                .receiverInfo(ReceiverInfo.of(order.getReceiverInfo()))
                .orderStatus(orderStatus)
                .orderDetails(OrderDetails.of(order))
                .build();
    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class OrderStatusHistory {
        private OrderStatus status;
        private LocalDateTime timestamp;

        public static OrderStatusHistory of(OrderHistory history) {
            return OrderStatusHistory.builder()
                    .status(history.getOrderStatus())
                    .timestamp(history.getUpdatedDate())
                    .build();
        }

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class OrderDetails {
        private LocalDateTime orderCreatedDate;
        private DeliveryZone deliveryZone;
        private double deliveryFee;
        private double CODAmount;
        private List<DeliveryMenResponse> assignedDeliveryMen;

        public static OrderDetails of(Order order) {

            List<DeliveryMenResponse> assignedDeliveryMen = new ArrayList<>();

            if(order.getPicker() != null){
                assignedDeliveryMen.add(DeliveryMenResponse.of(order.getPicker()));
            }

            if(order.getSender() != null){
                assignedDeliveryMen.add(DeliveryMenResponse.of(order.getSender()));
            }

            return OrderDetails.builder()
                    .orderCreatedDate(order.getCreatedDate())
                    .deliveryZone(DeliveryZone.of(order.getZoneInfo()))
                    .deliveryFee(order.getDeliveryFee())
                    .CODAmount(order.getCodAmount())
                    .assignedDeliveryMen(assignedDeliveryMen)
                    .build();
        }

        @Getter
        @Setter
        @Builder
        public static class DeliveryMenResponse {
            private String id;
            private String name;

            public static DeliveryMenResponse of(DeliveryMen deliveryMen){
                return DeliveryMenResponse.builder()
                        .id(deliveryMen.getId())
                        .name(deliveryMen.getName())
                        .build();
            }
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class DeliveryZone {
        private ZoneDetail from;
        private ZoneDetail to;

        public static DeliveryZone of(Order.ZoneInfo zoneInfo) {
            return DeliveryZone.builder()
                    .from(ZoneDetail.of(zoneInfo.getFromId(), zoneInfo.getFromName()))
                    .to(ZoneDetail.of(zoneInfo.getToId(), zoneInfo.getToName()))
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ZoneDetail {
        private String id;
        private String name;

        public static ZoneDetail of(String id, String name) {
            return ZoneDetail.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class SenderInfo {
        private String senderName;
        private String phoneNumber;
        private String address;
        private List<PackageDetails> packageDetails;

        public static SenderInfo of(Order.SenderInfo senderInfo) {
            return SenderInfo.builder()
                    .senderName(senderInfo.getSenderName())
                    .phoneNumber(senderInfo.getPhoneNumber())
                    .address(senderInfo.getAddress())
                    .packageDetails(senderInfo.getPackageDetails() != null ?
                            senderInfo.getPackageDetails().stream()
                                    .map(PackageDetails::of)
                                    .toList() :
                            List.of())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class PackageDetails {
        private String packageCategoryType;
        private String packageSize;
        private String packageWeight;

        public static PackageDetails of(Order.PackageDetails packageDetails) {
            return PackageDetails.builder()
                    .packageCategoryType(packageDetails.getPackageCategoryType())
                    .packageSize(packageDetails.getPackageSize())
                    .packageWeight(packageDetails.getPackageWeight())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class ReceiverInfo {
        private String receiverName;
        private String phoneNumber;
        private String address;

        public static ReceiverInfo of(Order.ReceiverInfo receiverInfo) {
            return ReceiverInfo.builder()
                    .receiverName(receiverInfo.getReceiverName())
                    .phoneNumber(receiverInfo.getPhoneNumber())
                    .address(receiverInfo.getAddress())
                    .build();
        }
    }

}