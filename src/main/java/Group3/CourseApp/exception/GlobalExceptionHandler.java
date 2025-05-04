package Group3.CourseApp.exception;


import Group3.CourseApp.dto.response.CommonResponse;
import Group3.CourseApp.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<Object>> handleCustomException(CustomException ex) {
        CommonResponse<Object> response = CommonResponse.<Object>builder().statusCode(ex.getHttpStatus().value()).message(ex.getMessage()).data(null).timestamp(null).build();
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        CommonResponse<Object> response = CommonResponse.<Object>builder().statusCode(HttpStatus.BAD_REQUEST.value()).message(errorMessage.toString()).data(null).timestamp(null).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation error: ");
        ex.getConstraintViolations().forEach(violation -> {
            errorMessage.append(violation.getMessage()).append("; ");
        });
        CommonResponse<Object> response = CommonResponse.<Object>builder().statusCode(HttpStatus.BAD_REQUEST.value()).message(errorMessage.toString()).data(null).timestamp(null).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Object>> handleGlobalException(Exception ex) {
        CommonResponse<Object> response = CommonResponse.<Object>builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).data(null).timestamp(null).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
