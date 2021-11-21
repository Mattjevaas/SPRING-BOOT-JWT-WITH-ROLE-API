package com.personal.JWTTemplate.controller;

import com.personal.JWTTemplate.error.NotFoundException;
import com.personal.JWTTemplate.model.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public WebResponse validationHandler(ConstraintViolationException constraintViolationException){
        return new WebResponse(){{
                code = 400;
                status = "BAD REQUEST";
                data = constraintViolationException.getMessage();
        }};
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public WebResponse notFoundCustom(){
        return new WebResponse(){{
            code = 404;
            status = "NOT FOUND";
            data = "Not Found";
        }};
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public WebResponse notFound(){
        return new WebResponse(){{
            code = 404;
            status = "NOT FOUND";
            data = "User not found in database";
        }};
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public WebResponse validationHandler(){
        return new WebResponse(){{
            code = 401;
            status = "UNAUTHORIZED";
            data = "Unauthorized Access";
        }};
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public WebResponse validationHandler(AccessDeniedException accessDeniedException){
        return new WebResponse(){{
            code = 403;
            status = "FORBIDDEN";
            data = accessDeniedException.getMessage();
        }};
    }
}
