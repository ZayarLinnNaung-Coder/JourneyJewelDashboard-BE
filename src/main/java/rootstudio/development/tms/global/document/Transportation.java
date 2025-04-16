package rootstudio.development.tms.global.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class Transportation extends BaseDocument{

    @Id
    private String id;

    private String name;
    private String description;
    private List<Price> priceList;

    @Getter
    @Setter
    public static class Price{
        private String placeId;
        private Double price;
    }

}
