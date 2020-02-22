package com.codemonkeys9.budgeit.dso.entry;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

abstract class DefaultEntry implements Entry {
    Amount amount;
    int entryID;
    int catID;
    Details details;
    Date date;

    DefaultEntry(Amount amount, int entryID, Details details, Date date,int catID) {

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
        this.catID = catID;
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

    @Override
    public int getCatID() {
        return this.catID;
    }

    @Override
    public Details getDetails() {
        return this.details;
    }

    @Override
    public Date getDate() {
        return this.date;
    }
}
