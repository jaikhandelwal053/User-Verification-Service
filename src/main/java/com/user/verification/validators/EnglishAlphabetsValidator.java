package com.user.verification.validators;

public class EnglishAlphabetsValidator implements Validator {
    private static EnglishAlphabetsValidator instance;

    private EnglishAlphabetsValidator() {}

    public static synchronized EnglishAlphabetsValidator getInstance() {
        if (instance == null) {
            instance = new EnglishAlphabetsValidator();
        }
        return instance;
    }

    @Override
    public boolean validate(Object value) {
        if (value instanceof String) {
            String str = (String) value;
            return str.matches("[a-zA-Z]+");
        }
        return false;
    }

	@Override
	public boolean validateoffset(Object value) {
		// TODO Auto-generated method stub
		return false;
	}
}

