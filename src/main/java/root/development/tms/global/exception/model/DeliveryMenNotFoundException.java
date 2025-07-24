package root.development.tms.global.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryMenNotFoundException extends RuntimeException{
    private final String errorCode;

    public DeliveryMenNotFoundException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
