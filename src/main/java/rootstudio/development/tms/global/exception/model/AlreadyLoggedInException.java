package rootstudio.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyLoggedInException extends RuntimeException{
    private final String errorCode;
    public AlreadyLoggedInException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
