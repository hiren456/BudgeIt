package com.codemonkeys9.budgeit.Entry;

import java.security.InvalidParameterException;
import java.util.Date;

class DefaultEntry implements Entry {
    private int amount;
    private int entryID;
    //private int catID;
    private String details;
    private Date date;

    DefaultEntry(int amount, int entryID, String details, Date date) {

        // check that parameters are valid
        if(date == null){

            throw new InvalidParameterException();
        }

        // details cannot be null
        // is this ok?
        if(details == null){

            throw new InvalidParameterException();
        }

        this.amount = amount;
        this.entryID = entryID;
        //this.catID = catID;
        this.details = details;
        this.date = date;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public int getEntryID() {
        return this.entryID;
    }

    //@Override
    //public int getCatID() {
    //    return this.catID;
    //}

    @Override
    public String getDetails() {
        return this.details;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public String getDisplayDate() {
        String displayDate = this.date.toGMTString();
        displayDate = displayDate.substring(0,displayDate.length()-13);
        return displayDate;
    }

    @Override
    public Entry modifyEntry(int amount, String details, Date date) {

        int newAmount = amount;
        int newEntryID = this.entryID;
        //int newCatID = catID;
        String newDetails = String.valueOf(details);
        Date newDate = new Date(date.getTime());

        return new DefaultEntry(newAmount,newEntryID,newDetails,newDate);
    }
}
