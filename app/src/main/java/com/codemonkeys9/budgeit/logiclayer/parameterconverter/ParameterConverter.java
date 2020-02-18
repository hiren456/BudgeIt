package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.dso.entry.Entry;

import java.util.Date;
import java.util.List;

public interface ParameterConverter {

    String createDisplayAmount(int amount);

    String createDisplayDate(Date date);

    int parseDisplayAmount(String displayAmount);

    Date parseDate(String date);

    List<Entry> createDisplayEntryList(List<Entry> list);
}
