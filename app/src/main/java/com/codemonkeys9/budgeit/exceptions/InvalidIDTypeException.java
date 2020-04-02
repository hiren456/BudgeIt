package com.codemonkeys9.budgeit.exceptions;

public class InvalidIDTypeException extends RuntimeException {
    String idType;

    public InvalidIDTypeException(String idType) {
        this.idType = idType;
    }

    public String getIDType() {
        return idType;
    }
}
