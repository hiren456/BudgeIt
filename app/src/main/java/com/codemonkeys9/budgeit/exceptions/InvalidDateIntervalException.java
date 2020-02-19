package com.codemonkeys9.budgeit.exceptions;

public class InvalidDateIntervalException extends UserInputException {
    public InvalidDateIntervalException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage(){
        return "The date interval that you typed is not valid. Please try again";
    }
}
