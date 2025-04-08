package rootstudio.development.dms.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import rootstudio.development.dms.global.constants.SuccessCodeConstants;
import rootstudio.development.dms.global.constants.SuccessCodeConstants.*;
import rootstudio.development.dms.global.domain.CustomResponse;

import java.time.OffsetDateTime;

public class BaseController {

    protected <T> ResponseEntity<CustomResponse<T>> createResponse(
            HttpStatus httpStatus, T data, String message, String successCode
    ){
        return createResponse(httpStatus,null, data, message, successCode);
    }

    protected <T> ResponseEntity<CustomResponse<T>> createResponse(
            HttpStatus httpStatus, MultiValueMap<String, String> headers, T data, String message,
            String successCode
    ){

        CustomResponse response = CustomResponse.builder()
                .data(data)
                .timestamp(OffsetDateTime.now())
                .message(message)
                .successCode(successCode)
                .build();

        return new ResponseEntity<>(response, headers, httpStatus);
    }

}