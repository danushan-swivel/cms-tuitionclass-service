package cms.tuitionclass.service.exception;

import cms.tuitionclass.service.enums.ErrorResponseStatus;
import cms.tuitionclass.service.wrapper.ErrorResponseWrapper;
import cms.tuitionclass.service.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * This method handle tuition class already exists exception response
     *
     * @param exception tuition class already exists exception
     * @return ErrorResponse/BadRequest
     */
    @ExceptionHandler(TuitionClassAlreadyExistsException.class)
    public ResponseEntity<ResponseWrapper> tuitionClassAlreadyExistException(TuitionClassAlreadyExistsException exception) {
        var wrapper = new ErrorResponseWrapper(ErrorResponseStatus.LOCATION_EXISTS, HttpStatus.BAD_REQUEST);
        log.error("The tuition class already exist. Error message: {}", exception.getMessage());
        return new ResponseEntity<>(wrapper, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handle invalid tuition class exception response
     *
     * @param exception invalid tuition class exception
     * @return ErrorResponse/BadRequest
     */
    @ExceptionHandler(InvalidTuitionClassException.class)
    public ResponseEntity<ResponseWrapper> invalidTuitionClassException(InvalidTuitionClassException exception) {
        var wrapper = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, HttpStatus.BAD_REQUEST);
        log.error("The retrieving the tuition class location is failed due to invalid location id. Error message: {}",
                exception.getMessage());
        return new ResponseEntity<>(wrapper, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handle tuition class exception response
     *
     * @param exception tuition class exception
     * @return ErrorResponse/InternalServerError
     */
    @ExceptionHandler(TuitionClassException.class)
    public ResponseEntity<ResponseWrapper> tuitionClassException(TuitionClassException exception) {
        var wrapper = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("The tuition class service is failed. Error message: {}", exception.getMessage());
        return new ResponseEntity<>(wrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
