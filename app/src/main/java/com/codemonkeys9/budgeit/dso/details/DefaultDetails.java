package com.codemonkeys9.budgeit.dso.details;

import com.codemonkeys9.budgeit.exceptions.InvalidDetailStringException;

class DefaultDetails implements Details {

    String details;

    DefaultDetails(String details) {
        validateDetails(details);
        this.details = details;
    }

    @Override
    public String getValue() {
        return this.details;
    }

    @Override
    public boolean equals(Details other) {
        return getValue().equals(other.getValue());
    }

    private void validateDetails(String details){
        if(details == null){
            throw new NullPointerException("details is null");
        }
        if(details.equals("")){
            throw new InvalidDetailStringException(details);
        }
    }

    @Override
    public DefaultDetails clone() {
        return new DefaultDetails(details);
    }
}

