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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("api/v1/tuition")
@RestController
public class TuitionClassController {
    private final TuitionClassService tuitionClassService;

    public TuitionClassController(TuitionClassService tuitionClassService) {
        this.tuitionClassService = tuitionClassService;
    }

    /**
     * Create tuition class
     *
     * @param tuitionClassRequestDto tuition class request dto
     * @return success/ error response
     */
    @PostMapping("")
    public ResponseEntity<ResponseWrapper> createTuitionClass(@RequestBody TuitionClassRequestDto tuitionClassRequestDto) {
        try {
            if (!tuitionClassRequestDto.isRequiredAvailable()) {
                var response = new ErrorResponseWrapper(ErrorResponseStatus.MISSING_REQUIRED_FIELDS, null);
                log.debug("The required fields {} are missing for create new tuition class", tuitionClassRequestDto.toJson());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            var tuitionClass = tuitionClassService.saveTuitionClass(tuitionClassRequestDto);
            var responseDto = new TuitionClassResponseDto(tuitionClass);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.LOCATION_CREATED, responseDto);
            log.debug("The tuition class is created successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            log.error("Creating new tuition class is failed for {}", tuitionClassRequestDto.toJson());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all tuition class detail
     *
     * @return success/ error response
     */
    @GetMapping("")
    public ResponseEntity<ResponseWrapper> getAllTuitionClasses() {
        try {
            var tuitionClassPage = tuitionClassService.getTuitionClassPage();
            var responseDto = new TuitionClassListResponseDto(tuitionClassPage);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.READ_LOCATION_LIST, responseDto);
            log.debug("Retrieving all tuition class details successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            log.error("Retrieving all tuition class details is failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get tuition class by tuition class id
     *
     * @param tuitionClassId tuition class id
     * @return success/ error response
     */
    @GetMapping("/{tuitionClassId}")
    public ResponseEntity<ResponseWrapper> getTuitionClassById(@PathVariable String tuitionClassId) {
        try {
            var tuitionClass = tuitionClassService.getTuitionClassById(tuitionClassId);
            var responseDto = new TuitionClassResponseDto(tuitionClass);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.READ_LOCATION, responseDto);
            log.debug("Tuition class retrieved successfully for tuition class id: {}", tuitionClassId);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (InvalidTuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, null);
            log.error("Retrieving tuition class is failed due to invalid tuition class id: {}", tuitionClassId);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            log.error("Retrieving tuition class is failed for tuition class id: {}", tuitionClassId);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update existing tuition class
     *
     * @param updateLocationRequestDto update location request dto
     * @return success/ error response
     */
    @PutMapping("")
    public ResponseEntity<ResponseWrapper> updateTuitionClass(@RequestBody UpdateTuitionClassRequestDto updateLocationRequestDto) {
        try {
            if (!updateLocationRequestDto.isRequiredAvailable()) {
                var response = new ErrorResponseWrapper(ErrorResponseStatus.MISSING_REQUIRED_FIELDS, null);
                log.debug("The required fields {} are missing for update tuition class id: {}",
                        updateLocationRequestDto.toJson(), updateLocationRequestDto.getTuitionClassId());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            var tuitionClass = tuitionClassService.updateTuitionClass(updateLocationRequestDto);
            var responseDto = new TuitionClassResponseDto(tuitionClass);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.LOCATION_UPDATED, responseDto);
            log.debug("The tuition class is updated successfully for tuition class id: {}", updateLocationRequestDto.getTuitionClassId());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (InvalidTuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, null);
            log.error("Updating tuition class is failed due to invalid tuition class id: {}", updateLocationRequestDto.getTuitionClassId());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            log.error("Updating tuition class is failed for tuition class id: {}", updateLocationRequestDto.getTuitionClassId());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete tuition class by tuition class id
     *
     * @param tuitionClassId tuition class id
     * @return success/ error response
     */
    @DeleteMapping("/{tuitionClassId}")
    public ResponseEntity<ResponseWrapper> deleteTuitionClass(@PathVariable String tuitionClassId) {
        try {
            tuitionClassService.deleteLocation(tuitionClassId);
            var successResponse = new SuccessResponseWrapper(SuccessResponseStatus.LOCATION_DELETED, null);
            log.debug("Tuition class is deleted successfully for tuition class id: {}", tuitionClassId);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (InvalidTuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INVALID_LOCATION, null);
            log.error("Deleting tuition class is failed due to invalid tuition class id: {}", tuitionClassId);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (TuitionClassException e) {
            var response = new ErrorResponseWrapper(ErrorResponseStatus.INTERNAL_SERVER_ERROR, null);
            log.error("Deleting tuition class is failed for tuition class id: {}", tuitionClassId);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
