package rootstudio.development.dms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import rootstudio.development.dms.global.enumeration.OrderStatus;

import java.time.LocalDateTime;

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
