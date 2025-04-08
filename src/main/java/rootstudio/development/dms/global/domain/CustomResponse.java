package rootstudio.development.dms.global.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rootstudio.development.dms.global.constants.SuccessCodeConstants;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
public class CustomResponse<T> {

    private T data;
    private String message;
    private OffsetDateTime timestamp;
//    private SuccessCodeConstants successCode;
    private String successCode;
}