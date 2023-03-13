package cms.tuitionclass.service.controller;

import cms.tuitionclass.service.domain.request.TuitionClassRequestDto;
import cms.tuitionclass.service.domain.request.UpdateTuitionClassRequestDto;
import cms.tuitionclass.service.domain.response.TuitionClassListResponseDto;
import cms.tuitionclass.service.domain.response.TuitionClassResponseDto;
import cms.tuitionclass.service.enums.ErrorResponseStatus;
import cms.tuitionclass.service.enums.SuccessResponseStatus;
import cms.tuitionclass.service.exception.InvalidTuitionClassException;
import cms.tuitionclass.service.exception.TuitionClassException;
import cms.tuitionclass.service.service.TuitionClassService;
import cms.tuitionclass.service.wrapper.ErrorResponseWrapper;
import cms.tuitionclass.service.wrapper.ResponseWrapper;
import cms.tuitionclass.service.wrapper.SuccessResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/tuition/")
@RestController
public class TuitionClassController {
    private final TuitionClassService tuitionClassService;

    public TuitionClassController(TuitionClassService tuitionClassService) {
        this.tuitionClassService = tuitionClassService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> createTuitionClass(@RequestBody TuitionClassRequestDto tuitionClassRequestDto) {
        try {
            if (!tuitionClassRequestDto.isRequiredAvailable()) {
                var response = new ErrorResponseWrapper(ErrorResponseStatus.MISSING_REQUIRED_FIELDS, null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            var tuitionClass = tuitionClassService.saveTuitionClass(tuitionClassRequestDto);
            var responseDto = new TuitionClassResponseDto(tuitionClass);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.LOCATION_CREATED, responseDto);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseWrapper> getAllTuitionClasses() {
        try {
            var tuitionClassPage = tuitionClassService.getTuitionClassPage();
            var responseDto = new TuitionClassListResponseDto(tuitionClassPage);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.READ_LOCATION_LIST, responseDto);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<ResponseWrapper> getTuitionClassById(@PathVariable String tuitionClassId) {
        try {
            if (tuitionClassId.trim().isEmpty() || tuitionClassId == null) {
                var response = new ErrorResponseWrapper(ErrorResponseStatus.MISSING_REQUIRED_FIELDS, null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            var tuitionClass = tuitionClassService.getTuitionClassById(tuitionClassId);
            var responseDto = new TuitionClassResponseDto(tuitionClass);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.READ_LOCATION, responseDto);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (InvalidTuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<ResponseWrapper> updateTuitionClass(@RequestBody UpdateTuitionClassRequestDto updateLocationRequestDto) {
        try {
            if (!updateLocationRequestDto.isRequiredAvailable()) {
                var response = new ErrorResponseWrapper(ErrorResponseStatus.MISSING_REQUIRED_FIELDS, null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            var tuitionClass = tuitionClassService.updateTuitionClass(updateLocationRequestDto);
            var responseDto = new TuitionClassResponseDto(tuitionClass);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.LOCATION_UPDATED, responseDto);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (InvalidTuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<ResponseWrapper> deleteTuitionClass(@PathVariable String tuitionClassId) {
        try {
            if (tuitionClassId.trim().isEmpty() || tuitionClassId == null) {
                var response = new ErrorResponseWrapper(ErrorResponseStatus.MISSING_REQUIRED_FIELDS, null);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            tuitionClassService.deleteLocation(tuitionClassId);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.LOCATION_DELETED, null);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (InvalidTuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
