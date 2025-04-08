package rootstudio.development.tms.features.wayManagement.orderManagement.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private List<OrderItemResponse> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private int numberOfElements;
    private boolean empty;

    public static OrderResponse of(Page<OrderItemResponse> page) {
        return new OrderResponse(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst(),
                page.getNumberOfElements(),
                page.isEmpty()
        );
    }
}
