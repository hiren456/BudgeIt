package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.Collections;
import java.util.List;

public class DefaultParameterConverter implements ParameterConverter {

    @Override
    public List<Entry> createDisplayEntryList(List<Entry> list) {
        // converts list from chronological to reverse chronological
        Collections.reverse(list);
        return list;
    }
}
