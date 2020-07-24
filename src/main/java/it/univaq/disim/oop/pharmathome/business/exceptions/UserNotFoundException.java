package it.univaq.disim.oop.pharmathome.business.exceptions;

public class UserNotFoundException extends BusinessException{

	public UserNotFoundException() {
		super();
		
	}

	public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
		

	public UserNotFoundException(String message) {
		super(message);
		
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
		
	}

}
