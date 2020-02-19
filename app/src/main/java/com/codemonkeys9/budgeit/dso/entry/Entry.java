package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface Entry {

    // getters
    Amount getAmount();
    int getEntryID();
    //Iteration 2.5 int getCatID();
    Details getDetails();
    Date getDate();

    //Iteration 2.5 Add flag/catID

    //Iteration 2.5 add the ability to flag/unflag
    //Iteration 2.5 add the ability to update catID
    //Iteration 2.5 takes values and returns an entry
    //Iteration 2.5 with those updated updated values
    Entry modifyEntry(Amount amount,Details details, Date date);

    boolean equals(Entry other);
}
