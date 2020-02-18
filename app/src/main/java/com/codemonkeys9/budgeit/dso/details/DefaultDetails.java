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

    private void validateDetails(String details){
        if(details == null){

            throw new NullPointerException();
        }

        // could check for too long string or too short string
    }

}

