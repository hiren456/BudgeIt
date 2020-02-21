package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.date.Date;

public class PurchaseFactory {
    public static Purchase createPurchase(Amount amount,int entryID, Details details,Date date,boolean flag){
        return new DefaultPurchase(amount,entryID,details,date,flag);
    }
    public static Purchase createPurchase(Amount amount,int entryID, Details details,Date date){
        return new DefaultPurchase(amount,entryID,details,date,false);
    }
}
