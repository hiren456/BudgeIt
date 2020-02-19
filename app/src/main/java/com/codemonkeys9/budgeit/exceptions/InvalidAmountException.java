package com.codemonkeys9.budgeit.exceptions;

public class InvalidAmountException extends UserInputException {
    public InvalidAmountException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage(){
        return "The amount that you typed is not valid. Please try again";
    }
}
