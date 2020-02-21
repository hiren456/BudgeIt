package com.codemonkeys9.budgeit.logiclayer.uientrymanager;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.logiclayer.entrycreator.EntryCreator;


class DefaultUIEntryManager implements UIEntryManager {
    EntryCreator entryCreator;

    DefaultUIEntryManager(EntryCreator entryCreator){
        this.entryCreator = entryCreator;
    }

    @Override
    public void createEntry(String amount, String details, String date,boolean purchase) {
        Amount parsedAmount = AmountFactory.fromString(amount);
        Details parsedDetails = DetailsFactory.fromString(details);
        Date parsedDate = DateFactory.fromString(date);

        if(purchase){
            this.entryCreator.createPurchase(parsedAmount,parsedDetails,parsedDate);
        }else{
            this.entryCreator.createIncome(parsedAmount,parsedDetails,parsedDate);
        }
    }
}
