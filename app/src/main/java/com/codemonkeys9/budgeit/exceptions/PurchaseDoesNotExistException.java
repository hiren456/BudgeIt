package com.codemonkeys9.budgeit.exceptions;

public class PurchaseDoesNotExistException extends EntryDoesNotExistException{
    public PurchaseDoesNotExistException(String errorMessage){
        super(errorMessage);
    }
}
