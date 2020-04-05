package com.codemonkeys9.budgeit.logiclayer.entryflagger;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;

class DefaultEntryFlagger implements EntryFlagger {
    @Override
    public void flagPurchase(int id, boolean flag)
            throws PurchaseDoesNotExistException {
        Database db = DatabaseHolder.getDatabase();

        BaseEntry entry = db.selectDefaultEntryByID(id);

        if(entry == null){
            throw new PurchaseDoesNotExistException("Purchase with id "+id+" does not exist");
        }
        Purchase purchase;
        if(entry instanceof Purchase){
            purchase = (Purchase) entry;
        }else{
            throw new PurchaseDoesNotExistException("Entry with id "+id+" is not a purchase");
        }

        Purchase newPurchase;
        if(flag) {
            newPurchase = purchase.flag();
        }else{
            newPurchase = purchase.unflag();
        }

        db.updateDefaultEntry(newPurchase);
    }
}
