package cms.tuitionclass.service.exception;

public class TuitionClassAlreadyExistsException extends TuitionClassException{
    public TuitionClassAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public TuitionClassAlreadyExistsException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
