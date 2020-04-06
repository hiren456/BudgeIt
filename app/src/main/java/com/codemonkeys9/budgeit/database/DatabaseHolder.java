package com.codemonkeys9.budgeit.database;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import java.util.List;

public class DatabaseHolder {
    private static Database db;

    public static synchronized void init() {
        if(db == null) {
            IDManager idManager = IDManagerFactory.createIDManager();
            db = DatabaseFactory.createDatabase(idManager.getInitialID("Entry"),idManager.getInitialID("Category"));
        }
        fillDB();
    }

    public static synchronized Database getDatabase() {
        return db;
    }

    //only for tests
    public static synchronized void initTestable(Database database) {
        db = database;
    }

    private static synchronized void fillDB(){
        UIEntryManager entryManager = UIEntryManagerFactory.createUIEntryManager();
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        EntryList entries = entryFetcher.fetchAllEntrys();
        List<BaseEntry> entryList = entries.getReverseChrono();

        if(entryList.isEmpty()) {
            UICategoryCreator categoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
            UIEntryCategorizer entryCategorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();

            int games = categoryCreator.createBudgetCategory("80", "Games");
            int misc = categoryCreator.createBudgetCategory("200", "Miscellaneous");
            int income = categoryCreator.createSavingsCategory("2000", "Income");
            int transportation = categoryCreator.createBudgetCategory("100", "Transportation");
            int alyx = entryManager.createEntry("60", "Half-Life: Alyx Pre-order", "2019-12-01", true);
            System.out.println("Games has id: "+games);
            System.out.println("Misc has id: "+misc);
            System.out.println("Income has id: "+income);
            System.out.println("Transportation has id: "+transportation);
            entryCategorizer.categorizeEntry(alyx, games);
            ADD_FAKE_DATA:
            for (int year = 2018; year <= 2020; year++) {
                for (int month = 1; month <= 12; month++) {
                    if (year == 2020 && month > 4) break ADD_FAKE_DATA;
                    // ensures that month has two digits
                    String monthString;
                    if (month < 10) {
                        monthString = "0" + month;
                    } else {
                        monthString = "" + month;
                    }

                    // Gas every week-ish
                    for (int j = 0; j < 4; j++) {
                        int day = j * 7 + 1;
                        // ensures that a day has two digits
                        String dayString;
                        if (day < 10) {
                            dayString = "0" + day;
                        } else {
                            dayString = "" + day;
                        }

                        if(year < 2020 || day < 6) {
                            int entry = entryManager.createEntry("50", "Gas", year + "-" + monthString + "-" + dayString, true);
                            entryCategorizer.categorizeEntry(entry, transportation);
                        }
                    }
                    // Paycheck every two weeks-ish
                    int pay1 = entryManager.createEntry("1000", "Paycheck", year + "-" + monthString + "-01", false);
                    entryCategorizer.categorizeEntry(pay1, income);

                    if(year < 2020) {
                        int pay2 = entryManager.createEntry("1000", "Paycheck", year + "-" + monthString + "-15", false);
                        entryCategorizer.categorizeEntry(pay2, income);

                        int what = entryManager.createEntry(
                                "120",
                                "Something with an extremely, exceptionally, extraordinarily, staggeringly, shockingly, positively supercalifragilisticexpialidociously long description",
                                year + "-" + monthString + "-13",
                                true
                        );
                        entryCategorizer.categorizeEntry(what, misc);
                    }
                }
            }
            entries = entryFetcher.fetchAllEntrys();
        }
    }
}

