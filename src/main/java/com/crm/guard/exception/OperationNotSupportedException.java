package com.crm.guard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.BAD_REQUEST )
public class OperationNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = 6087070117882705652L;

	public OperationNotSupportedException() {
        super();
    }

    public OperationNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotSupportedException(String message) {
        super(message);
    }

    public OperationNotSupportedException(Throwable cause) {
        super(cause);
    }

}
