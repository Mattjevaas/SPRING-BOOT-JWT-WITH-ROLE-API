package com.personal.JWTTemplate.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class ValidationUtil{
    private final Validator validator;

    public void validate(Object any){
        final Set result = validator.validate(any);
        if(result.size() != 0){
            throw new ConstraintViolationException(result);
        }
    }
}
