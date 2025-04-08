package rootstudio.development.tms.features.wayManagement.orderManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.tms.global.document.Order;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {

    private String id;
    private OrderDetails orderDetails;
    private SenderInfo senderInfo;
    private ReceiverInfo receiverInfo;

    public static CreateOrderResponse of(Order order) {
        return CreateOrderResponse.builder()
                .id(order.getId())
                .orderDetails(OrderDetails.of(order.getZoneInfo(), order.getDeliveryFee(), order.getCodAmount()))
                .senderInfo(SenderInfo.of(order.getSenderInfo()))
                .receiverInfo(ReceiverInfo.of(order.getReceiverInfo()))
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class OrderDetails {
        private DeliveryZone deliveryZone;
        private double deliveryFee;
        private double codAmount;

        public static OrderDetails of(Order.ZoneInfo zoneInfo, double deliveryFee, double codAmount) {
            return OrderDetails.builder()
                    .deliveryZone(DeliveryZone.of(zoneInfo))
                    .deliveryFee(deliveryFee)
                    .codAmount(codAmount)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
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
    public static class ZoneDetail {
        private String id;
        private String name;

        public static ZoneDetail of(String id, String zoneName) {
            return ZoneDetail.builder()
                    .id(id)
                    .name(zoneName)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
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
