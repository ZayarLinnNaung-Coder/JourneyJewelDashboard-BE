package rootstudio.development.dms.global.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import rootstudio.development.dms.global.enumeration.AccountStatus;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@Builder
public class Admin extends BaseDocument {

    @Id
    private String id;
    private String username;

    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDateTime lastDispatchTime;
    private AccountStatus accountStatus;

    @DocumentReference
    private AdminRole role;

}
