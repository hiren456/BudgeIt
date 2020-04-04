package com.codemonkeys9.budgeit.application;

import android.app.Application;

import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManagerFactory;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class BudApplication extends Application {
    private static BudApplication bContext;

    @Override
    public void onCreate() {
        super.onCreate();
        bContext = this;

        DatabaseHolder.init();

        // This is necessary for LocalDate to work with
        // API < 23
        AndroidThreeTen.init(this);

        UIRecurringEntryManager recurringEntryManager = UIRecurringEntryManagerFactory.createUIReccuringEntryManager();
        recurringEntryManager.checkAllRecurringEntrys();
    }

    public static BudApplication getContext() {
        return bContext;
    }
}
