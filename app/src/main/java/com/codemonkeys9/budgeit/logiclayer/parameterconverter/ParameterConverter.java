package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.List;

public interface ParameterConverter {
    List<Entry> createDisplayEntryList(List<Entry> list);
}
