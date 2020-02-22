package com.codemonkeys9.budgeit.exceptions;

public class EntryDoesNotExistException extends RuntimeException {
    public EntryDoesNotExistException(String msg){
        super(msg);
    }
}
