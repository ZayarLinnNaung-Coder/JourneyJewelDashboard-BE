package rootstudio.development.dms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {

    private String errorCode;

    public BusinessException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}