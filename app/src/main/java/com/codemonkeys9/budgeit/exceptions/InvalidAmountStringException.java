package com.codemonkeys9.budgeit.exceptions;

public class InvalidAmountStringException extends RuntimeException {
    public InvalidAmountStringException(String errorMessage) {
        super(errorMessage);
    }
}
