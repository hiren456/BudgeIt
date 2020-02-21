package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entryfetcher.EntryFetcher;


class DefaultUIEntryManager implements UIEntryManager {
    EntryCreator entryCreator;
    EntryFetcher fetcher;

    DefaultUIEntryManager(EntryCreator entryCreator,EntryFetcher fetcher){
        this.entryCreator = entryCreator;
        this.fetcher = fetcher;
    }

    @Override
    public void createEntry(String amount, String details, String date,boolean purchase) {
        Amount parsedAmount = AmountFactory.fromString(amount);
        Details parsedDetails = DetailsFactory.fromString(details);
        Date parsedDate = DateFactory.fromString(date);

        if(purchase){
            this.entryCreator.createPurchase(parsedAmount,parsedDetails,parsedDate);
        }else{
            this.entryCreator.createIncome(parsedAmount,parsedDetails,parsedDate);
        }
    }

    @Override
    public void flagPurchase(int id, boolean flag) throws PurchaseDoesNotExistException {
        Entry entry = fetcher.fetchByID(id);
        Database db = DatabaseHolder.getDatabase();

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

        db.updateEntry(newPurchase);
    }

    @Override
    public void flagPurchase(Entry entry, boolean flag) throws PurchaseDoesNotExistException {
        flagPurchase(entry.getEntryID(),flag);
    }
}
