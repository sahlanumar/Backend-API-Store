package Group3.CourseApp.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    
    private final HttpStatus httpStatus;
    private final String message;

    public CustomException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public CustomException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
