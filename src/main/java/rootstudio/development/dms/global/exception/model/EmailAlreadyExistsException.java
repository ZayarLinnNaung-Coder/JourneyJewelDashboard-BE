package rootstudio.development.dms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAlreadyExistsException extends RuntimeException{
    private final String errorCode;

    public EmailAlreadyExistsException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
