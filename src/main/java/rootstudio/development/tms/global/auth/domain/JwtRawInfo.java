package rootstudio.development.tms.global.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtRawInfo {

    private String userId;

}
