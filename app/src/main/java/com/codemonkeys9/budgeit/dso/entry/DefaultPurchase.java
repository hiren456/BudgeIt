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
    public Purchase flag() {
        Amount newAmount = AmountFactory.fromInt(this.amount.getValue());
        int newEntryID = this.entryID;
        int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(this.details.getValue());
        Date newDate = DateFactory.fromInts(this.date.getYear(),this.date.getMonth(),this.date.getDay());

        return new DefaultPurchase(newAmount,newEntryID,newDetails,newDate,newCatID,true);
    }

    @Override
    public Purchase unflag() {
        Amount newAmount = AmountFactory.fromInt(this.amount.getValue());
        int newEntryID = this.entryID;
        int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(this.details.getValue());
        Date newDate = DateFactory.fromInts(this.date.getYear(),this.date.getMonth(),this.date.getDay());

        return new DefaultPurchase(newAmount,newEntryID,newDetails,newDate,newCatID,false);
    }

    @Override
    public Purchase modifyEntry(Amount amount, Details details, Date date) {

        Amount newAmount = AmountFactory.fromInt(amount.getValue());
        int newEntryID = this.entryID;
        int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(details.getValue());
        Date newDate = DateFactory.fromInts(date.getYear(),date.getMonth(),date.getDay());

        return new DefaultPurchase(newAmount,newEntryID,newDetails,newDate,newCatID,this.flag);
    }

    @Override
    public Entry changeCategory(int catID) {
        Amount newAmount = AmountFactory.fromInt(this.amount.getValue());
        int newEntryID = this.entryID;
        int newCatID = catID;
        Details newDetails = DetailsFactory.fromString(this.details.getValue());
        Date newDate = DateFactory.fromInts(this.date.getYear(),this.date.getMonth(),this.date.getDay());

        return new DefaultPurchase(newAmount,newEntryID,newDetails,newDate,newCatID,this.flag);
    }

    @Override
    public boolean equals(Entry other) {
        boolean idSame = getEntryID() == other.getEntryID();
        boolean amountSame = getAmount().equals(other.getAmount());
        boolean detailsSame = getDetails().equals(other.getDetails());
        boolean dateSame = getDate().equals(other.getDate());

        boolean flaggedSame = false;
        boolean typeSame = false;

        if(other instanceof Purchase){
            typeSame = true;
            flaggedSame = flagged() == ((Purchase) other).flagged();
        }

        return idSame && amountSame && detailsSame
                && dateSame && typeSame && flaggedSame;
    }
}
