package rootstudio.development.dms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumberAlreadyExistsException extends RuntimeException{
    private final String errorCode;

    public PhoneNumberAlreadyExistsException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
