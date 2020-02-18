package com.codemonkeys9.budgeit.logiclayer.entrylistfilterer;
import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.List;

public interface EntryListFilterer {

    void getIncome(List<Entry> list);
    void getPurchases(List<Entry> list);
}
