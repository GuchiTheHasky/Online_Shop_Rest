package the.husky.onlineshoprest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import the.husky.onlineshoprest.exception.item.ItemException;
import the.husky.onlineshoprest.exception.item.ItemNotFoundException;
import the.husky.onlineshoprest.exception.user.UserAlreadyExistException;
import the.husky.onlineshoprest.exception.user.UserException;
import the.husky.onlineshoprest.exception.user.UserNotFoundException;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException e) {
        String message = e.getMessage();
        ExceptionEntity exception = new ExceptionEntity(message, HttpStatus.CONFLICT, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        String message = e.getMessage();
        ExceptionEntity exception = new ExceptionEntity(message, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException e) {
        String message = e.getMessage();
        ExceptionEntity exception = new ExceptionEntity(message, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<?> handleItemNotFoundException(ItemNotFoundException e) {
        String message = e.getMessage();
        ExceptionEntity exception = new ExceptionEntity(message, HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemException.class)
    public ResponseEntity<?> handleItemException(ItemException e) {
        String message = e.getMessage();
        ExceptionEntity exception = new ExceptionEntity(message, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
