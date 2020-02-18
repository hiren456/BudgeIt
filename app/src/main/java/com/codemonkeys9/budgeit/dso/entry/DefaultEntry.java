package com.codemonkeys9.budgeit.dso.entry;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

class DefaultEntry implements Entry {
    Amount amount;
    int entryID;
    //private int catID;
    Details details;
    Date date;

    DefaultEntry(Amount amount, int entryID, Details details, Date date) {

        // check that parameters are valid
        if(date == null){

            throw new NullPointerException();
        }

        if(details == null){

            throw new NullPointerException();
        }

        if(amount == null){

            throw new NullPointerException();
        }

        this.amount = amount;
        this.entryID = entryID;
        //this.catID = catID;
        this.details = details;
        this.date = date;
    }

    @Override
    public Amount getAmount() {
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
    public Details getDetails() {
        return this.details;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public Entry modifyEntry(Amount amount, Details details, Date date) {

        Amount newAmount = amount;
        int newEntryID = this.entryID;
        //int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(this.details.getValue());
        Date newDate = DateFactory.fromInts(this.date.getYear(),this.date.getMonth(),this.date.getDay());

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
                && getAmount().equals(other.getAmount())
                //&& getCatID() == other.getCatID()
                && getDetails().equals(other.getDetails())
                && getDate().equals(other.getDate());
    }

}
