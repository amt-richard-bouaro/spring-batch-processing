package com.amalitech.springbatchprocessing.exception;

import com.amalitech.springbatchprocessing.dto.ErrorResponseDto;
import com.amalitech.springbatchprocessing.enums.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.lang.model.type.NullType;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    
    @ExceptionHandler(ImportException.class)
    public ResponseEntity<ErrorResponseDto<NullType>> handleImportException(
            ImportException exception,
            WebRequest request
    ){
       ErrorResponseDto<NullType> responseDTO = new ErrorResponseDto<>(ResponseStatus.ERROR,
                "Import failed", Instant.now().toString(), null);
        
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto<Map<String, String>>> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        
        exception.getBindingResult()
                 .getAllErrors()
                 .forEach(error -> {
                     if (error instanceof FieldError fieldError) {
                         errors.put("field", fieldError.getField());
                         errors.put("cause", fieldError.getDefaultMessage());
                     } else {
                         errors.put(error.getObjectName(), error.getDefaultMessage());
                     }
                 });
        
        ErrorResponseDto<Map<String, String>> responseDTO = new ErrorResponseDto<>(ResponseStatus.ERROR,
                "Validation Error: Payload not well formed", Instant.now()
                                                                    .toString(), errors);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
