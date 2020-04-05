package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

/*
This interface defines data that is shared between recurring and concrete entries
 */
public interface BaseEntry {
    Amount getAmount();
    int getCatID();
    Details getDetails();
    Date getDate();
    int getEntryID();

    BaseEntry modifyEntry(Amount amount, Details details, Date date);
    BaseEntry changeCategory(int catID);

    BaseEntry clone();
    boolean equals(BaseEntry other);
}
