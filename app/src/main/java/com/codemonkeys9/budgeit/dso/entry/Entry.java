package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface Entry {

    // getters
    Amount getAmount();
    int getEntryID();
    int getCatID();
    Details getDetails();
    Date getDate();

    Entry modifyEntry(Amount amount,Details details, Date date);

    boolean equals(Entry other);
}
