package com.codemonkeys9.budgeit.logiclayer.uientrybuilder;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriodFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManagerFactory;

public class EntryBuilder implements  UIEntryBuilder{
    Database db;
    private int entryID;

    EntryBuilder(Database db){ this.db = db; }

    public void buildBaseEntry(String amount, String details, String date, boolean expense){
        UIEntryManager entryManager = UIEntryManagerFactory.createUIEntryManager();
        entryID = entryManager.createEntry(amount, details,date, expense);
    }

    public void addFlag(boolean flag){
        UIEntryManager entryManager = UIEntryManagerFactory.createUIEntryManager();
        entryManager.flagPurchase(entryID, flag);
    }
    public void addCategory(int c){
        UIEntryCategorizer entryCategorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();
        entryCategorizer.categorizeEntry(entryID, c);
    }
    public void addRecurrencePeriod(int days, int weeks, int months, int years){
        RecurrencePeriod recurrencePeriod = RecurrencePeriodFactory.createRecurrencePeriod(days, weeks, months, years);
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        Entry e = entryFetcher.fetchEntryByID(entryID);
        UIRecurringEntryManager recurringEntryManager = UIRecurringEntryManagerFactory.createUIReccuringEntryManager();

        recurringEntryManager.createRecurringEntry(entryID, recurrencePeriod);
    }
    public int getEntryID(){
        return entryID;
    }


}
