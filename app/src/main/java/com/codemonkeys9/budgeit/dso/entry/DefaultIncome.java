package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

class DefaultIncome extends DefaultEntry implements Income {
    DefaultIncome(Amount amount, int entryID, Details details, Date date,int catID) {
        super(amount, entryID, details, date,catID);
    }

    @Override
    public Income modifyEntry(Amount amount, Details details, Date date) {

        Amount newAmount = AmountFactory.fromInt(amount.getValue());
        int newEntryID = this.entryID;
        int newCatID = this.catID;
        Details newDetails = DetailsFactory.fromString(details.getValue());
        Date newDate = DateFactory.fromInts(date.getYear(),date.getMonth(),date.getDay());

        return new DefaultIncome(newAmount,newEntryID,newDetails,newDate,newCatID);
    }

    @Override
    public Entry changeCategory(int catID) {
        Amount newAmount = AmountFactory.fromInt(this.amount.getValue());
        int newEntryID = this.entryID;
        int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(this.details.getValue());
        Date newDate = DateFactory.fromInts(this.date.getYear(),this.date.getMonth(),this.date.getDay());

        return new DefaultIncome(newAmount,newEntryID,newDetails,newDate,newCatID);
    }

    @Override
    public boolean equals(Entry other) {
        boolean idSame = getEntryID() == other.getEntryID();
        boolean amountSame = getAmount().equals(other.getAmount());
        boolean detailsSame = getDetails().equals(other.getDetails());
        boolean dateSame = getDate().equals(other.getDate());
        boolean typeSame = other instanceof Income;

        return idSame && amountSame && detailsSame
                && dateSame && typeSame;
    }
}
