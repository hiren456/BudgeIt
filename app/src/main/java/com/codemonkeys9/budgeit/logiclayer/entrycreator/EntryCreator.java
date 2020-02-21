package com.codemonkeys9.budgeit.logiclayer.entrycreator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface EntryCreator {

    /*
    Creates an Entry object using other DSO and stores it in the datebase
     */
    void createEntry(Amount amount, Details details, Date date);
}