package rootstudio.development.tms.global.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String errorCode;
    private String errorMessage;

}