package rootstudio.development.tms.global.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country extends BaseDocument{

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private String countryCode;
}
