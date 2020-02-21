package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

class DefaultIncome extends DefaultEntry implements Income {
    DefaultIncome(Amount amount, int entryID, Details details, Date date) {
        super(amount, entryID, details, date);
    }

    @Override
    public Income modifyEntry(Amount amount, Details details, Date date) {

        Amount newAmount = AmountFactory.fromInt(amount.getValue());
        int newEntryID = this.entryID;
        //int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(details.getValue());
        Date newDate = DateFactory.fromInts(date.getYear(),date.getMonth(),date.getDay());

        return new DefaultIncome(newAmount,newEntryID,newDetails,newDate);
    }
}
