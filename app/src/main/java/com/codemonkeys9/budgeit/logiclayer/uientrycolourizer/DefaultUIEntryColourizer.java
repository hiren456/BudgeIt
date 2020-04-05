package com.codemonkeys9.budgeit.logiclayer.uientrycolourizer;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;

class DefaultUIEntryColourizer implements UIEntryColourizer {
    private static final int RED   = 0xFFFF0000;
    private static final int GREEN = 0xFF00AA00;
    private static final int BLACK = 0xFF000000;

    public int getDescriptionColour(BaseEntry entry) {
        if(entry instanceof Purchase) {
            Purchase purchase = (Purchase)entry;
            if(purchase.flagged()) {
                return RED;
            }
        }
        if(entry instanceof RecurringPurchase) {
            RecurringPurchase purchase = (RecurringPurchase)entry;
            if(purchase.flagged()) {
                return RED;
            }
        }
        return BLACK;
    }

    public int getAmountColour(BaseEntry entry) {
        if(entry instanceof Purchase || entry instanceof RecurringPurchase) {
            return RED;
        } else {
            return GREEN;
        }
    }
}
