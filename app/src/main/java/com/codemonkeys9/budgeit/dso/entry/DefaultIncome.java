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
    public DefaultIncome modifyEntry(Amount amount, Details details, Date date) {
        return (DefaultIncome)super.modifyEntry(amount, details, date);
    }

    @Override
    public DefaultIncome changeCategory(int catID) {
        return (DefaultIncome)super.changeCategory(catID);
    }

    @Override
    public DefaultIncome clone() {
        return new DefaultIncome(amount.clone(), entryID, details.clone(), date.clone(), catID);
    }
}
