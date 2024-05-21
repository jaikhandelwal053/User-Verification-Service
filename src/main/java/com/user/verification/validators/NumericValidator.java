package com.user.verification.validators;

public class NumericValidator implements Validator {
    private static NumericValidator instance;

    private NumericValidator() {}

    public static synchronized NumericValidator getInstance() {
        if (instance == null) {
            instance = new NumericValidator();
        }
        return instance;
    }

    @Override
    public boolean validate(Object value) {
        if (value instanceof Integer) {
            int num = (int) value;
            return num >= 1 && num <= 5;
        }
        return false;
    }

	@Override
	public boolean validateoffset(Object value) {
		if (value instanceof Integer) {
            int num = (int) value;
            return num >= 0 && num < 5;
        }
        return false;
	}
}
