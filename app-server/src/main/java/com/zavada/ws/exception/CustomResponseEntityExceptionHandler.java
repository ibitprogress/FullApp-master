package com.zavada.ws.exception;

import com.zavada.ws.domain.exception.ExceptionResponse;
import com.zavada.ws.domain.exception.ValidationExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(Exception e, WebRequest req) {
        ExceptionResponse exResponse = ExceptionResponse
                .builder()
                .message(e.getMessage())
                .details(req.getDescription(false))
                .build();
        return new ResponseEntity<>(exResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e, WebRequest req) {
        ExceptionResponse exResponse = ExceptionResponse
                .builder()
                .message(e.getMessage())
                .details(req.getDescription(false))
                .build();
        return new ResponseEntity<>(exResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ValidationExceptionResponse> validationExceptionResponses =
                fieldErrors.stream().map(f ->
                        ValidationExceptionResponse.builder()
                                .field(f.getField())
                                .message(f.getDefaultMessage())
                                .build()).collect(Collectors.toList());

        return new ResponseEntity<>(validationExceptionResponses, HttpStatus.BAD_REQUEST);
    }
}
