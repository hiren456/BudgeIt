package com.codemonkeys9.budgeit.exceptions;

public class InvalidDateStringException extends RuntimeException {
    public InvalidDateStringException(String errorMessage){
        super(errorMessage);
    }
}
