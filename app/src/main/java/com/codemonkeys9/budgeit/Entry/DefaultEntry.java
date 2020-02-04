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
    public String getDisplayAmount() {
        String result = Integer.toString(amount);
        // Make sure there are at least three digits: one before the decimal point and two after
        while(result.length() < 3) {
            result = '0' + result;
        }
        int decimalPointPos = result.length() - 2;
        String beforeDecimalPoint = result.substring(0, decimalPointPos);
        String afterDecimalPoint = result.substring(decimalPointPos);
        return beforeDecimalPoint + '.' + afterDecimalPoint;
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

    // NOTE: what it means for two entries to be equal doesn't change based on the specific
    // implementation. Actually, it's hard to imagine *anything* changing between implementations
    // since the Entry interface is just a data model, but that's besides the point. Anyway, we
    // should consider making Entry an abstract class and putting this implementation in its body.
    // I tried making this a defaulted method in the interface body as well, but that requires API
    // version 24, and we only have v23 available to us on the Nexus 7 :(
    //     - Zach
    @Override
    public boolean equals(Entry other) {
        return getEntryID() == other.getEntryID()
                && getAmount() == other.getAmount()
                //&& getCatID() == other.getCatID()
                && getDetails() == other.getDetails()
                && getDate() == other.getDate();
    }
}
