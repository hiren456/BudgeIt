package com.codemonkeys9.budgeit.dso.entry;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

class DefaultPurchase extends DefaultEntry implements Purchase {
    boolean flag;

    DefaultPurchase(Amount amount, int entryID, Details details, Date date,int catID,boolean flag) {
        super(amount, entryID, details, date,catID);
        this.flag = flag;
    }

    @Override
    public boolean flagged() {
        return this.flag;
    }

    @Override
    public DefaultPurchase flag() {
        DefaultPurchase purchase = this.clone();
        purchase.flag = true;
        return purchase;
    }

    @Override
    public DefaultPurchase unflag() {
        DefaultPurchase purchase = this.clone();
        purchase.flag = false;
        return purchase;
    }

    @Override
    public DefaultPurchase clone() {
        return new DefaultPurchase(amount.clone(), entryID, details.clone(), date.clone(), catID, flag);
    }

    @Override
    public boolean equals(BaseEntry other) {
        if(!super.equals(other)) return false;

        if(other instanceof Purchase) {
            return flagged() == ((Purchase) other).flagged();
        } else {
            throw new RuntimeException("If `other` is not an instance of Purchase, super.equals() should return false");
        }
    }
}
