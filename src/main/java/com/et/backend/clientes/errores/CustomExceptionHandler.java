package com.et.backend.clientes.errores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice // pone en escucha de los errores del controlador
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{

	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Server Error", details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        ErrorResponse errorMessage;
        List<String> details = new ArrayList<>();
        ErrorResponse error;
        if (mostSpecificCause != null) {

            String message = mostSpecificCause.getMessage();
            details.add( message );
            error = new ErrorResponse("Fallo de validación numero", details);
        } else {
            details.add( ex.getMessage() );
            error = new ErrorResponse("erre", details);
        }
        return new ResponseEntity(error, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Fallo de validación", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler( DataAccessException.class )
    protected ResponseEntity<Object> erroSql( DataAccessException ex, WebRequest request) {
        // lista de detalles del Objeto ErrorResponse
        List<String> details = new ArrayList<>();
        details.add( ex.getLocalizedMessage() );
        // creamos nuestro objeto de respuesta ErrorResponse
        ErrorResponse error = new ErrorResponse("SQL ERROR", details);
        //devolvemos el ResponseEntity con los errores capturados
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler( RecordNotFoundException.class )
    public final ResponseEntity<Object> handleUserNotFoundException( RecordNotFoundException ex,
                                                                    WebRequest request ) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

}