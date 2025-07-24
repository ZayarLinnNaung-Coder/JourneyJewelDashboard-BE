package root.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundCommonException extends RuntimeException {

    private String errorCode;

    public NotFoundCommonException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}