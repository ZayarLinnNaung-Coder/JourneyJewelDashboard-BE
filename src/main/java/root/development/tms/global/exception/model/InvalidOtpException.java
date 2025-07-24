package root.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidOtpException extends RuntimeException{

    private String errorCode;
        public InvalidOtpException(String errorCode, String message) {
            super(message);
        }
}
