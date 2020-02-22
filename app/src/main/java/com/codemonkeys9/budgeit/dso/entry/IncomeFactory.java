package com.codemonkeys9.budgeit.dso.entry;


import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.date.Date;


public class IncomeFactory {
    public static Income createIncome(Amount amount, int entryID, Details details, Date date,int catID){
        return new DefaultIncome(amount, entryID, details, date,catID);
    }
}
