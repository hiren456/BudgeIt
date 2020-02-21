package com.codemonkeys9.budgeit.logiclayer.entrycreator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface EntryCreator {

    /*
    Creates a Purchase object using other DSO and stores it in the database
    if the flag is not provided it is assumed to be false
     */
    void createPurchase(Amount amount, Details details, Date date);
    void createPurchase(Amount amount, Details details, Date date,boolean flag);

    /*
    Creates at Income object using other DSO and stores it in the database
     */
    void createIncome(Amount amount, Details details, Date date);
}