package rootstudio.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPasswordException extends RuntimeException {

    private String errorCode;

    public InvalidPasswordException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}
