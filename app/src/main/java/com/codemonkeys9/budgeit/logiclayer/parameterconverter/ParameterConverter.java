package com.codemonkeys9.budgeit.logiclayer.parameterconverter;

import com.codemonkeys9.budgeit.entry.Entry;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountStringException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateStringException;

import java.time.LocalDate;
import java.util.List;

public interface ParameterConverter {

    String createDisplayAmount(int amount);

    String createDisplayDate(LocalDate date);

    int parseDisplayAmount(String displayAmount) throws InvalidAmountStringException;

    LocalDate parseDate(String date) throws InvalidDateStringException;

    List<Entry> createDisplayEntryList(List<Entry> list);
}
