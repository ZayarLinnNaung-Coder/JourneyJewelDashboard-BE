package rootstudio.development.tms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import rootstudio.development.tms.global.enumeration.OrderStatus;

@Document
@Getter
@Setter
@Builder
public class OrderHistory extends BaseDocument{

    @Id
    private String id;
    private OrderStatus orderStatus;

    @Indexed
    private String orderId;

}
