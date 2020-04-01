package com.codemonkeys9.budgeit.exceptions;

public class InvalidDetailStringException extends UserInputException {
    public InvalidDetailStringException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage() {
        return "The details that you typed are not valid, please try again";
    }
}
