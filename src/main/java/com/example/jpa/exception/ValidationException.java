package com.example.jpa.exception;

public class ValidationException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2166020284148381697L;
    private static final String DEFAULT = "Verification failure";

    public ValidationException(String string) {
        super(string);
    }

    public ValidationException() {
        super(DEFAULT);
    }

}
