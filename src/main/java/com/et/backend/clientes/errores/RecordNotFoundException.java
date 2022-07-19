package com.et.backend.clientes.errores;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException
{
    /**
     * Esta clase maneja errores de datos no encontrados
     */
    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String exception) {
        super(exception);
    }
}