package rootstudio.development.dms.global.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import rootstudio.development.dms.features.wayManagement.orderManagement.domain.request.CreateOrderRequest;
import rootstudio.development.dms.global.enumeration.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseDocument{

    @Id
    private String id;
    private SenderInfo senderInfo;
    private ReceiverInfo receiverInfo;
    private OrderStatus orderStatus;
    private double deliveryFee;
    private double codAmount;
    private LocalDateTime orderCreatedDateTime;
    private LocalDateTime deliveredDateTime;
    private ZoneInfo zoneInfo;

    @DocumentReference
    private DeliveryMen picker;

    @DocumentReference
    private DeliveryMen sender;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SenderInfo {
        private String senderName;
        private String phoneNumber;
        private String address;
        private List<PackageDetails> packageDetails;

        public static SenderInfo from(CreateOrderRequest.SenderInfo senderInfo) {
            return new SenderInfo(
                    senderInfo.getSenderName(),
                    senderInfo.getPhoneNumber(),
                    senderInfo.getAddress(),
                    senderInfo.getPackageDetails().stream()
                            .map(PackageDetails::from)
                            .toList()
            );
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReceiverInfo {
        private String receiverName;
        private String phoneNumber;
        private String address;

        public static ReceiverInfo from(CreateOrderRequest.ReceiverInfo receiverInfo) {
            return new ReceiverInfo(
                    receiverInfo.getReceiverName(),
                    receiverInfo.getPhoneNumber(),
                    receiverInfo.getAddress()
            );
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PackageDetails {
        private String packageCategoryType;
        private String packageSize;
        private String packageWeight;

        public static PackageDetails from(CreateOrderRequest.PackageDetails packageDetails) {
            return new PackageDetails(
                    packageDetails.getPackageCategoryType(),
                    packageDetails.getPackageSize(),
                    packageDetails.getPackageWeight() // Convert String to double
            );
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ZoneInfo {
        private String fromId;
        private String fromName;
        private String toId;
        private String toName;
        public static ZoneInfo from(CreateOrderRequest.DeliveryZone deliveryZone) {
            return new ZoneInfo(
                    deliveryZone.getFrom().getId(),
                    deliveryZone.getFrom().getName(),
                    deliveryZone.getTo().getId(),
                    deliveryZone.getTo().getName()
            );
        }
    }

}
