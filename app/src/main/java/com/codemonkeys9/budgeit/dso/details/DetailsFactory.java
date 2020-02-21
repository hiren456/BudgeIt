package com.codemonkeys9.budgeit.dso.details;

public class DetailsFactory {
    public static Details fromString(String details){

        return new DefaultDetails(details);
    }
}
