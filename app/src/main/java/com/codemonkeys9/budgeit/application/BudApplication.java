package com.codemonkeys9.budgeit.application;

import android.app.Application;

public class BudApplication extends Application {
    private static BudApplication bContext;

    @Override
    public void onCreate() {
        super.onCreate();
        bContext = this;
    }

    public static BudApplication getContext() {
        return bContext;
    }
}
