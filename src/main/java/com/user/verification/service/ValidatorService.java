package com.user.verification.service;


import com.user.verification.validators.Validator;
import com.user.verification.validators.ValidatorFactory;

public class ValidatorService {

    private static ValidatorService instance;

    private ValidatorService() {}

    public static synchronized ValidatorService getInstance() {
        if (instance == null) {
            instance = new ValidatorService();
        }
        return instance;
    }

    public boolean validate(Object value) {
        Validator validator = ValidatorFactory.getValidator(value);
        return validator != null && validator.validate(value);
    }
    public boolean validateoffset(Object value) {
        Validator validator = ValidatorFactory.getValidator(value);
        return validator != null && validator.validateoffset(value);
    }
}
