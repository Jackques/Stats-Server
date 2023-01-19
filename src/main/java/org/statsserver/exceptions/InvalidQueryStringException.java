package org.statsserver.exceptions;

public class InvalidQueryStringException extends Exception {
    private String message;

    public InvalidQueryStringException(String message){
        super(message);
        this.message = message;
    }

}
