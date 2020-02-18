package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;
import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.List;

public interface EntryListFilterer {

    public void getIncome(List<Entry> list);
    public void getPurchases(List<Entry> list);
}
