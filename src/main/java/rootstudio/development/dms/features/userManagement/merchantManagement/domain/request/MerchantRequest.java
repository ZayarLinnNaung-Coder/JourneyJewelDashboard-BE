package rootstudio.development.dms.features.userManagement.merchantManagement.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantRequest {
    private String query;
    private int page = 0;
    private int size = 10;
}
