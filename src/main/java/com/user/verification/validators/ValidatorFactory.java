package com.user.verification.validators;

public class ValidatorFactory {
    public static Validator getValidator(Object value) {
        if (value instanceof String) {
            return EnglishAlphabetsValidator.getInstance();
        } else if (value instanceof Integer) {
            return NumericValidator.getInstance();
        }
        return null;
    }
}