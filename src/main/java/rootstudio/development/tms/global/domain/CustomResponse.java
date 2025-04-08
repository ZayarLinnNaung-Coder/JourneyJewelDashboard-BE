package rootstudio.development.tms.global.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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