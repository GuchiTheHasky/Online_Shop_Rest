package the.husky.onlineshoprest.exception;

import org.springframework.http.HttpStatus;

public record ExceptionEntity(String message, HttpStatus httpStatus, int httpStatusCode) {
}
