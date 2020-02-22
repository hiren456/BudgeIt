package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.date.Date;

public class PurchaseFactory {
    public static Purchase createPurchase(Amount amount,int entryID, Details details,Date date,int catID,boolean flag){
        return new DefaultPurchase(amount,entryID,details,date,catID,flag);
    }
    public static Purchase createPurchase(Amount amount,int entryID, Details details,Date date,int catID){
        return new DefaultPurchase(amount,entryID,details,date,catID,false);
    }
}
