package it.univaq.disim.oop.pharmathome.business.exceptions;

public class FarmacoNotFoundException extends BusinessException{

	public FarmacoNotFoundException() {
		super();
	
	}

	public FarmacoNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public FarmacoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public FarmacoNotFoundException(String message) {
		super(message);
	
	}

	public FarmacoNotFoundException(Throwable cause) {
		super(cause);
	
	}


}
