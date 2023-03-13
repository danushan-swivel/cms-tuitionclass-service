package cms.tuitionclass.service.exception;

public class TuitionClassException extends RuntimeException{
    public TuitionClassException(String errorMessage) {
        super(errorMessage);
    }

    public TuitionClassException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
