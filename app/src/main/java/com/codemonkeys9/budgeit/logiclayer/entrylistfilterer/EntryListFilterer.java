package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;
import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.List;

/*
This class is for filtering a list of entries
based on whether they are a purchase or an income
 */
public interface EntryListFilterer {

    /*
    Takes a list of entries and
    returns it with only income entries
    ie. only entries with a positive amount
     */
    void getIncome(List<Entry> list);

    /*
    Takes a list of entries and
    returns it with only purchase entries
    ie. only entries with a negative amount
     */
    void getPurchases(List<Entry> list);
}
