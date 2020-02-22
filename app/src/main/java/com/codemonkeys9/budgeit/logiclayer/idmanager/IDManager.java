package com.codemonkeys9.budgeit.logiclayer.idmanager;

import com.codemonkeys9.budgeit.exceptions.InvalidIDTypeException;

/*
This class manages all id's for entries and categories
 */
public interface IDManager {
    /*
    Gets the starting id for either "Entry" or "Category"
     */
    int getInitialID(String idName)
            throws InvalidIDTypeException;
    /*
    Gets the Default id "Category"
     */
    int getDefaultID(String idName)
            throws InvalidIDTypeException;
    /*
    Gets a new for either "Entry" or "Category"
     */
    int getNewID(String idName)
            throws InvalidIDTypeException;
}
