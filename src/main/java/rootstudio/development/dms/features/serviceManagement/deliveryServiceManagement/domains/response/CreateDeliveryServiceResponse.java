package rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import rootstudio.development.dms.global.document.DeliveryService;
import rootstudio.development.dms.global.document.Zone;

@Getter
@Setter
@Builder
public class CreateDeliveryServiceResponse {
    private String id;
    private String serviceId;
    private String name;
    private DeliveryZoneResponse fromZone;
    private DeliveryZoneResponse toZone;
    private double price;
    private String description;
    private String iconUrl;

    public static CreateDeliveryServiceResponse of(DeliveryService document) {
        Zone fromZoneDoc = document.getFromZone();
        Zone toZoneDoc = document.getToZone();

        return CreateDeliveryServiceResponse.builder()
                .id(document.getId())
                .serviceId(document.getServiceId())
                .name(document.getName())
                .fromZone(fromZoneDoc != null ? DeliveryZoneResponse.of(fromZoneDoc) : null)
                .toZone(toZoneDoc != null ? DeliveryZoneResponse.of(toZoneDoc) : null)
                .price(document.getPrice())
                .description(document.getDescription())
                .iconUrl(document.getIconUrl())
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class DeliveryZoneResponse {
        private String id;
        private String zoneId;
        private String name;

        public static DeliveryZoneResponse of(Zone zone) {
            return DeliveryZoneResponse.builder()
                    .id(zone.getId())
                    .zoneId(zone.getZoneId())
                    .name(zone.getName())
                    .build();
        }
    }
}