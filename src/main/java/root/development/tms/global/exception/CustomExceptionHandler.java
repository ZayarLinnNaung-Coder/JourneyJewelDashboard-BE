package root.development.tms.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import root.development.tms.global.constants.ErrorCodeConstants;
import root.development.tms.global.domain.ErrorResponse;
import root.development.tms.global.exception.model.*;
import root.development.tms.global.utils.MessageBundle;

import java.time.LocalDateTime;

@ControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler {

    private final MessageBundle messageBundle;

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ErrorResponse> handleAccountStatusException(AccountStatusException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActivationNeedException.class)
    public ResponseEntity<ErrorResponse> handleActivationNeedException(ActivationNeedException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.OK);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundCommonException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundCommonException(NotFoundCommonException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<String> handleEmailSendingException(EmailSendingException ex) {
        return ResponseEntity.status(500).body("Failed to send email: " + ex.getMessage());
    }

    // Token Exceptions
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ErrorCodeConstants.ERR_JWT001, messageBundle.getMessage(ErrorCodeConstants.ERR_JWT001), LocalDateTime.now()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ErrorCodeConstants.ERR_JWT002, messageBundle.getMessage(ErrorCodeConstants.ERR_JWT002), LocalDateTime.now()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ErrorCodeConstants.ERR_JWT003, messageBundle.getMessage(ErrorCodeConstants.ERR_JWT003), LocalDateTime.now()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MerchantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMerchantNotFoundException(MerchantNotFoundException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DeliveryMenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeliveryMenNotFoundException(DeliveryMenNotFoundException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyLoggedInException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyLoggedInException(AlreadyLoggedInException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOtpException(InvalidOtpException ex) {
        return new ResponseEntity<>(
                createErrorResponse(ex.getErrorCode(), ex.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(String errorCode, String errorMessage, LocalDateTime timeStamp){
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .timestamp(timeStamp)
                .build();
    }

}
