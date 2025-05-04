package Group3.CourseApp.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    INVALID_USER_DATA(HttpStatus.BAD_REQUEST, "Invalid user data"),
    EMAIL_ALREADY_REGISTERED(HttpStatus.CONFLICT, "Email already registered"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Invalid password"),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "Invalid role"),
    INVALID_USER_ROLE(HttpStatus.BAD_REQUEST, "Invalid user role"),

    TAX_NOT_FOUND(HttpStatus.NOT_FOUND, "Tax not found"),
    INVALID_TAX_DATA(HttpStatus.BAD_REQUEST, "Invalid tax data"),
    TAX_ALREADY_EXISTS(HttpStatus.CONFLICT, "Tax already exists"),

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
    INVALID_PRODUCT_DATA(HttpStatus.BAD_REQUEST, "Invalid product data"),
    PRODUCT_ALREADY_EXISTS(HttpStatus.CONFLICT, "Product already exists"),

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "Menu not found"),
    INVALID_MENU_DATA(HttpStatus.BAD_REQUEST, "Invalid menu data"),
    MENU_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "Menu not available"),

    //Customer related errors
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not found"),
    INVALID_CUSTOMER_DATA(HttpStatus.BAD_REQUEST, "Invalid customer data"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),
    PHONENUMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Phone already exists"),

    //Transaction related errors
    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Transaction not found"),
    INVALID_TRANSACTION_DATA(HttpStatus.BAD_REQUEST, "Invalid transaction data"),

    //TRANSACTION DETAIL related errors
    TRANSACTION_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "Transaction detail not found"),
    INVALID_TRANSACTION_DETAIL_DATA(HttpStatus.BAD_REQUEST, "Invalid transaction detail data"),
    
    // Generic errors
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
