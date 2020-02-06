package com.codemonkeys9.budgeit.logiclayer.entryfetcher.entrylistorderer;

import com.codemonkeys9.budgeit.entry.Entry;

import java.util.Collections;
import java.util.List;

class DefaultEntryListOrderer implements EntryListOrderer {
    @Override
    public void orderEntryList(List<Entry> list) {
        // entryFetcher returns entry in reverse chronological order
        //Collections.reverse(list);
    }
}
