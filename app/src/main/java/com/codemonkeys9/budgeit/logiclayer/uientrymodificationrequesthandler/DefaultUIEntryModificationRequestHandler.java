package com.codemonkeys9.budgeit.logiclayer.uientrymodificationrequesthandler;

import com.codemonkeys9.budgeit.entry.DisplayConverter;
import com.codemonkeys9.budgeit.logiclayer.ParameterConverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;

import java.util.Date;

class DefaultUIEntryModificationRequestHandler implements UIEntryModificationRequestHandler {
    ParameterConverter converter;
    EntryCreator entryCreator;

    DefaultUIEntryModificationRequestHandler(ParameterConverter converter, EntryCreator entryCreator){
        this.converter = converter;
        this.entryCreator = entryCreator;
    }

    @Override
    public void createEntry(String amount, String details, String date) {
        int parsedAmount = DisplayConverter.parseDisplayAmount(amount);
        String parsedDetails = details;
        Date parsedDate = this.converter.parseDate(date);

        this.entryCreator.createEntry(parsedAmount,parsedDetails,parsedDate);
    }
}
