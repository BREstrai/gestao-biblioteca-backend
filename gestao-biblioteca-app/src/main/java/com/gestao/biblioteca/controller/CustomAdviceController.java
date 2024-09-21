package com.gestao.biblioteca.controller;

import com.brestrai.utils.commons.controller.AbstractCustomAdviceController;
import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.gestao.biblioteca.exceptions.DevolucaoException;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.exceptions.UsuarioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Objects.nonNull;

@ControllerAdvice
public class CustomAdviceController extends AbstractCustomAdviceController {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorResponse> handleUsuarios(UsuarioException e) {

        if (nonNull(e.getErrorResponse())) {

            ErrorResponse errorResponse = e.getErrorResponse();

            return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
        }

        return handleException(e);
    }

    @ExceptionHandler(LivroException.class)
    public ResponseEntity<ErrorResponse> handleLivros(LivroException e) {

        if (nonNull(e.getErrorResponse())) {

            ErrorResponse errorResponse = e.getErrorResponse();

            return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
        }

        return handleException(e);
    }

    @ExceptionHandler(DevolucaoException.class)
    public ResponseEntity<ErrorResponse> handleEmprestimos(DevolucaoException e) {

        if (nonNull(e.getErrorResponse())) {

            ErrorResponse errorResponse = e.getErrorResponse();

            return new ResponseEntity<>(errorResponse, errorResponse.getStatusCode());
        }

        return handleException(e);
    }
}
