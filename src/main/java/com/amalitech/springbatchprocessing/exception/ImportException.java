package com.amalitech.springbatchprocessing.exception;

public class ImportException extends RuntimeException{
    
    public ImportException(String message) {
        super(message);
        
    }
    
    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
