package com.codemonkeys9.budgeit.exceptions;

/*
A blanket exception for all inputs from the user that are invalid
 */
public abstract class UserInputException extends RuntimeException {
    public UserInputException(String errorMessage){
        super(errorMessage);
    }
    public String getUserErrorMessage(){
        return "Something was wrong with your input, please try again.";
    }
}
