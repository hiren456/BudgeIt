package com.codemonkeys9.budgeit.exceptions;

public class IncomeDoesNotExistException extends EntryDoesNotExistException {
    IncomeDoesNotExistException(String msg) {
        super(msg);
    }
}
