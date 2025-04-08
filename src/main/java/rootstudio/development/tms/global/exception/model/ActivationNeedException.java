package rootstudio.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationNeedException extends RuntimeException {

    private String errorCode;

    public ActivationNeedException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}
