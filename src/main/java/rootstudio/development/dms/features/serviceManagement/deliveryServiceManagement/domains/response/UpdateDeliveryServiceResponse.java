package rootstudio.development.dms.features.serviceManagement.deliveryServiceManagement.domains.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.document.DeliveryService;
import rootstudio.development.dms.global.document.Zone;

@Getter
@Setter
@Builder
public class UpdateDeliveryServiceResponse {
    private String name;
    private ZoneResponse fromZone;
    private ZoneResponse toZone;
    private double price;
    private String description;
    private String iconUrl;

    public static UpdateDeliveryServiceResponse of(DeliveryService deliveryService){

        Zone fromZoneDoc = deliveryService.getFromZone();
        Zone toZoneDoc = deliveryService.getToZone();

        return UpdateDeliveryServiceResponse.builder()
                .name(deliveryService.getName())
                .fromZone(fromZoneDoc != null ? ZoneResponse.of(fromZoneDoc) : null)
                .toZone(toZoneDoc != null ? ZoneResponse.of(toZoneDoc) : null)
                .price(deliveryService.getPrice())
                .description(deliveryService.getDescription())
                .iconUrl(deliveryService.getIconUrl()).build();
    }

    @Getter
    @Setter
    @Builder
    public static class ZoneResponse {
        private String id;
        private String name;

        public static ZoneResponse of(Zone zone) {
            return ZoneResponse.builder()
                    .id(zone.getId())
                    .name(zone.getName())
                    .build();
        }
    }

}
