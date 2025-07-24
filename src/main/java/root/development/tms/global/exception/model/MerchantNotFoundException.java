package root.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantNotFoundException extends RuntimeException{
    private final String errorCode;

    public MerchantNotFoundException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}