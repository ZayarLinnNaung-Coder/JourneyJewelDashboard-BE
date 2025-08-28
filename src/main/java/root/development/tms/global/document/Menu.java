package root.development.tms.global.document;

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
public class Menu extends BaseDocument {

    @Id
    private String id;
    private String name;
    private String menuCode;
    private String url;
    private String icon;

    @DocumentReference
    private List<Menu> children;

    private String parentMenuId;

}