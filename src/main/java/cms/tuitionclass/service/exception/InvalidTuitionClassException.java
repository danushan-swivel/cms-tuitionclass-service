package cms.tuitionclass.service.exception;

public class InvalidTuitionClassException extends TuitionClassException{
    public InvalidTuitionClassException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidTuitionClassException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
