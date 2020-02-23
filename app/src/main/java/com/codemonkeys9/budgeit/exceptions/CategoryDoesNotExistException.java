package com.codemonkeys9.budgeit.exceptions;

public class CategoryDoesNotExistException extends RuntimeException {
    public CategoryDoesNotExistException(String msg){
        super(msg);
    }
}
