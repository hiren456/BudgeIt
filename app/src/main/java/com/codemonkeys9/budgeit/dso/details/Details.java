package com.codemonkeys9.budgeit.dso.details;

/*
This object represents a the details that a user
adds to describe an entry
 */
public interface Details {

    /*
    gets a string representing the details
    modifying this string should not modify the details in any way
     */
    String getValue();

    boolean equals(Details other);
    Details clone();
}
