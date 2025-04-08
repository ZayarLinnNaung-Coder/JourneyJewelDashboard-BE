package rootstudio.development.dms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document
@Getter
@Setter
@Builder
public class Zone extends BaseDocument{

    @Id
    private String id;
    private String name;
    private String zoneId;

    @DocumentReference
    private Country country;

    @DocumentReference
    private List<City> cities;

    @DocumentReference
    private List<Township> townships;

}
