package com.codemonkeys9.budgeit.logiclayer.uientrymodificationrequesthandler;

import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;

import java.time.LocalDate;

class DefaultUIEntryModificationRequestHandler implements UIEntryModificationRequestHandler {
    ParameterConverter converter;
    EntryCreator entryCreator;

    DefaultUIEntryModificationRequestHandler(ParameterConverter converter, EntryCreator entryCreator){
        this.converter = converter;
        this.entryCreator = entryCreator;
    }

    @Override
    public void createEntry(String amount, String details, String date) {
        int parsedAmount = this.converter.parseDisplayAmount(amount);
        String parsedDetails = details;
        LocalDate parsedDate = this.converter.parseDate(date);

        this.entryCreator.createEntry(parsedAmount,parsedDetails,parsedDate);
    }
}
