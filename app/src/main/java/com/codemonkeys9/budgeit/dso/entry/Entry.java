package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface Entry extends BaseEntry {

    @Override
    Entry modifyEntry(Amount amount,Details details, Date date);
    @Override
    Entry changeCategory(int catID);
}
