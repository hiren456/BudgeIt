package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

abstract class DefaultBaseEntry implements BaseEntry {
    Amount amount;
    int catID;
    Details details;
    Date date;

    DefaultBaseEntry(Amount amount, Details details, Date date, int catID) {

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
        this.catID = catID;
        this.details = details;
        this.date = date;
    }

    @Override
    public Amount getAmount() {
        return this.amount;
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

    @Override
    public abstract DefaultBaseEntry clone();

    @Override
    public DefaultBaseEntry modifyEntry(Amount amount,Details details, Date date) {
        DefaultBaseEntry entry = this.clone();
        entry.amount = amount.clone();
        entry.details = details.clone();
        entry.date = date.clone();
        return entry;
    }

    @Override
    public DefaultBaseEntry changeCategory(int catID) {
        DefaultBaseEntry entry = this.clone();
        entry.catID = catID;
        return entry;
    }

    @Override
    public boolean equals(BaseEntry other) {
        boolean amountSame = getAmount().equals(other.getAmount());
        boolean detailsSame = getDetails().equals(other.getDetails());
        boolean dateSame = getDate().equals(other.getDate());
        boolean typeSame = this.getClass() == other.getClass();

        return amountSame && detailsSame
                && dateSame && typeSame;
    }
}
