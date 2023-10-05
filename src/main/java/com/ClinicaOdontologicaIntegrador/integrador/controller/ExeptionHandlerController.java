package com.ClinicaOdontologicaIntegrador.integrador.controller;

import com.ClinicaOdontologicaIntegrador.integrador.Entities.Responses.ResponseObject;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.InvalidParameterExeption;
import com.ClinicaOdontologicaIntegrador.integrador.Exceptions.ResourceNotFoundExeption;
import org.apache.log4j.Logger;
import org.hibernate.HibernateError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExeptionHandlerController {
    private static final Logger log = Logger.getLogger(ExeptionHandlerController.class);
    @Autowired
    private HttpServletRequest request;





    @ExceptionHandler({InvalidParameterExeption.class, ResourceNotFoundExeption.class, HibernateError.class, Exception.class})
    public ResponseEntity<ResponseObject> exeption(Exception e){
        log.error(e);
        return ResponseEntity.badRequest().body(new ResponseObject(LocalDateTime.now(), String.valueOf(HttpStatus.BAD_REQUEST.value()) + ", " + HttpStatus.BAD_REQUEST.getReasonPhrase(), false, e.getLocalizedMessage(), request.getRequestURL()));
    }



}
