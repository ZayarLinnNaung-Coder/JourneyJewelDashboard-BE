package rootstudio.development.dms.features.wayManagement.orderManagement.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchOrderRequest {
    private String query;
    private int page;
    private int size;
}
