package cms.tuitionclass.service.controller;

import cms.tuitionclass.service.domain.response.ResponseDto;
import cms.tuitionclass.service.enums.ErrorResponseStatus;
import cms.tuitionclass.service.enums.SuccessResponseStatus;
import cms.tuitionclass.service.wrapper.ErrorResponseWrapper;
import cms.tuitionclass.service.wrapper.ResponseWrapper;
import cms.tuitionclass.service.wrapper.SuccessResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The base controller for generate response
 */
public class BaseController {
    /**
     * This method generate successful response
     *
     * @param statusMessage success response message
     * @param data          data
     * @param httpStatus    http status
     * @return Success Response
     */
    public ResponseEntity<ResponseWrapper> getSuccessResponse(SuccessResponseStatus statusMessage,
                                                              ResponseDto data, HttpStatus httpStatus) {
        var wrapper = new SuccessResponseWrapper(statusMessage, data, httpStatus);
        return new ResponseEntity<>(wrapper, httpStatus);
    }

    /**
     * This method generate error response
     *
     * @param statusMessage error response message
     * @return Error Response
     */
    public ResponseEntity<ResponseWrapper> getErrorResponse(ErrorResponseStatus statusMessage) {
        var wrapper = new ErrorResponseWrapper(statusMessage, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(wrapper, HttpStatus.BAD_REQUEST);
    }
}
