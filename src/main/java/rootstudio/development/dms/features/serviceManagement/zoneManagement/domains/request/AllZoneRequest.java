package rootstudio.development.dms.features.serviceManagement.zoneManagement.domains.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllZoneRequest {
    private String query;
    private int page;
    private int size;
}
