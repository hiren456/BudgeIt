package com.codemonkeys9.budgeit.exceptions;

public class PurchaseDoesNotExistException extends RuntimeException{
    public PurchaseDoesNotExistException(String errorMessage){
        super(errorMessage);
    }
}
