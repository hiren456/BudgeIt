package com.codemonkeys9.budgeit.LogicLayer.EntryListFilterer;
import com.codemonkeys9.budgeit.Entry.Entry;

import java.util.List;

public interface EntryListFilterer {

    public void getIncome(List<Entry> list);
    public void getPurchases(List<Entry> list);
}
