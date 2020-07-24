package it.univaq.disim.oop.pharmathome.business.exceptions;

public class ConnectionException extends BusinessException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
