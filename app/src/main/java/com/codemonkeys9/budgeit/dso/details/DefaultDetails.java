package com.codemonkeys9.budgeit.dso.details;

class DefaultDetails implements Details {

    String details;

    DefaultDetails(String details) {
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

            throw new NullPointerException();
        }
    }

    @Override
    public DefaultDetails clone() {
        return new DefaultDetails(details);
    }
}

