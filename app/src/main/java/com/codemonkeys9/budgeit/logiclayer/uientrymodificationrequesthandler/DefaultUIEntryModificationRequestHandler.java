package com.codemonkeys9.budgeit.logiclayer.uientrymodificationrequesthandler;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.logiclayer.parameterconverter.ParameterConverter;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;


class DefaultUIEntryModificationRequestHandler implements UIEntryModificationRequestHandler {
    ParameterConverter converter;
    EntryCreator entryCreator;

    DefaultUIEntryModificationRequestHandler(ParameterConverter converter, EntryCreator entryCreator){
        this.converter = converter;
        this.entryCreator = entryCreator;
    }

    @Override
    public void createEntry(String amount, String details, String date) {
        Amount parsedAmount = AmountFactory.fromString(amount);
        Details parsedDetails = DetailsFactory.fromString(details);
        Date parsedDate = DateFactory.fromString(date);

        this.entryCreator.createEntry(parsedAmount,parsedDetails,parsedDate);
    }
}
