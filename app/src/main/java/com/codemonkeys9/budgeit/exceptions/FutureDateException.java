package com.codemonkeys9.budgeit.exceptions;

//when a user tries to create an entry that will happen in the future.
public class FutureDateException extends UserInputException {

    public FutureDateException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage(){
        return "The date that you typed is in the future. Please try again";
    }
}
