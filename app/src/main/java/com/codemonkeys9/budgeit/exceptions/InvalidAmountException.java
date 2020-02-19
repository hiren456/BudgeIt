package com.codemonkeys9.budgeit.exceptions;

/*
An exception for when the user inputs an invalid amount
 */
public class InvalidAmountException extends UserInputException {
    public InvalidAmountException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage(){
        return "The amount that you typed is not valid. Please try again";
    }
}
