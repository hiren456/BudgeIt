package com.codemonkeys9.budgeit.exceptions;

public class InvalidDateException extends UserInputException{
    public InvalidDateException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage(){
        return "The date that you typed is not valid. Please try again";
    }
}
