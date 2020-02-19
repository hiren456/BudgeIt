package com.codemonkeys9.budgeit.exceptions;

/*
An exception for when the user attempts to enter an invalid date interval,
note that this should only get thrown when the dates are valid
otherwise a InvalidDateException should get thrown
 */
public class InvalidDateIntervalException extends UserInputException {
    public InvalidDateIntervalException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getUserErrorMessage(){
        return "The date interval that you typed is not valid. Please try again";
    }
}
