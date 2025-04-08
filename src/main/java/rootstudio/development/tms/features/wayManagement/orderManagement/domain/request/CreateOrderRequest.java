package rootstudio.development.tms.features.wayManagement.orderManagement.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    private SenderInfo senderInfo;
    private ReceiverInfo receiverInfo;
    private OrderDetails orderDetails;
    @Getter
    @Setter
    public static class OrderDetails {
        private DeliveryZone deliveryZone;
        private double deliveryFee;
        private double codAmount;
    }

    @Getter
    @Setter
    public static class DeliveryZone {
        private ZoneDetail from;
        private ZoneDetail to;
    }

    @Getter
    @Setter
    public static class ZoneDetail {
        private String id;
        private String name;
    }

    @Getter
    @Setter
    public static class SenderInfo {
        private String senderName;
        private String phoneNumber;
        private String address;
        private List<PackageDetails> packageDetails;
    }

    @Getter
    @Setter
    public static class PackageDetails {
        private String packageCategoryType;
        private String packageSize;
        private String packageWeight;
    }

    @Getter
    @Setter
    public static class ReceiverInfo {
        private String receiverName;
        private String phoneNumber;
        private String address;
    }

}
