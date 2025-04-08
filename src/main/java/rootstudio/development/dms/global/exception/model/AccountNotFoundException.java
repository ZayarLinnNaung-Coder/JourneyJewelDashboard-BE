package rootstudio.development.dms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountNotFoundException extends RuntimeException {

    private String errorCode;

    public AccountNotFoundException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
