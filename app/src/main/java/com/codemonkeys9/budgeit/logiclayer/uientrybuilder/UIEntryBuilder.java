package com.codemonkeys9.budgeit.logiclayer.uientrybuilder;

public interface UIEntryBuilder {

    void buildBaseEntry(String amount, String date, String details, boolean expense);
    void addFlag(boolean flag);
    void addCategory(int c);
    void addRecurrencePeriod(int days, int weeks, int months, int years);
    int getEntryID();
}
