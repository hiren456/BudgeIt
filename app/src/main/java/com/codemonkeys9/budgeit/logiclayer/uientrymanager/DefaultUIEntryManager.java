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
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.PurchaseDoesNotExistException;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;
import com.codemonkeys9.budgeit.logiclayer.entryflagger.EntryFlagger;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;


class DefaultUIEntryManager implements UIEntryManager {
    EntryCreator entryCreator;
    EntryFlagger flagger;
    UIEntryCategorizer categorizer;

    DefaultUIEntryManager(EntryCreator entryCreator, EntryFlagger flagger, UIEntryCategorizer categorizer){
        this.entryCreator = entryCreator;
        this.flagger = flagger;
        this.categorizer = categorizer;
    }

    @Override
    public void deleteEntry(Entry entry) throws EntryDoesNotExistException {
        deleteEntry(entry.getEntryID());
    }

    @Override
    public void deleteEntry(int entryID) throws EntryDoesNotExistException {
        Database db  = DatabaseHolder.getDatabase();

        // db returns false if entry is not found
        if(!db.deleteDefaultEntry(entryID)){
            throw new EntryDoesNotExistException("Entry with ID " + entryID +" does not exist");
        }
    }

    @Override
    public int createEntry(String amount, String details, String date, boolean purchase) {
        Amount parsedAmount = AmountFactory.fromString(amount);
        Details parsedDetails = DetailsFactory.fromString(details);
        Date parsedDate = DateFactory.fromString(date);

        if(purchase){
            return entryCreator.createPurchase(parsedAmount,parsedDetails,parsedDate);
        }else{
            return entryCreator.createIncome(parsedAmount,parsedDetails,parsedDate);
        }
    }

    @Override
    public int createEntry(String amount, String details, String date, boolean purchase, int catID) throws InvalidDateException, InvalidAmountException, FutureDateException, CategoryDoesNotExistException {
        Amount parsedAmount = AmountFactory.fromString(amount);
        Details parsedDetails = DetailsFactory.fromString(details);
        Date parsedDate = DateFactory.fromString(date);

        if(purchase){
            int id = entryCreator.createPurchase(parsedAmount,parsedDetails,parsedDate);
            categorizer.categorizeEntry(id,catID);
            return id;
        }else{
            int id = entryCreator.createIncome(parsedAmount,parsedDetails,parsedDate);
            categorizer.categorizeEntry(id,catID);
            return id;
        }
    }

    @Override
    public void flagPurchase(int id, boolean flag) throws PurchaseDoesNotExistException {
        flagger.flagPurchase(id,flag);
    }

    @Override
    public void flagPurchase(Entry entry, boolean flag) throws PurchaseDoesNotExistException {
        flagger.flagPurchase(entry.getEntryID(),flag);
    }
}
