package rootstudio.development.dms.features.wayManagement.orderManagement.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateOrderRequest {
    private CreateOrderRequest.SenderInfo senderInfo;
    private CreateOrderRequest.ReceiverInfo receiverInfo;
    private CreateOrderRequest.OrderDetails orderDetails;
    @Getter
    @Setter
    public static class OrderDetails {
        private CreateOrderRequest.DeliveryZone deliveryZone;
        private double deliveryFee;
        private double CODAmount;
    }

    @Getter
    @Setter
    public static class DeliveryZone {
        private CreateOrderRequest.ZoneDetail from;
        private CreateOrderRequest.ZoneDetail to;
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
        private List<CreateOrderRequest.PackageDetails> packageDetails;
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
