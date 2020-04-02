package com.codemonkeys9.budgeit.logiclayer.uientrycolourizer;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;

class DefaultUIEntryColourizer implements UIEntryColourizer {
    private static final int RED   = 0xFFFF0000;
    private static final int GREEN = 0xFF00AA00;
    private static final int BLACK = 0xFF000000;

    public int getDescriptionColour(Entry entry) {
        if(entry instanceof Purchase) {
            Purchase purchase = (Purchase)entry;
            if(purchase.flagged()) {
                return RED;
            }
        }
        return BLACK;
    }

    public int getAmountColour(Entry entry) {
        if(entry instanceof Purchase) {
            return RED;
        } else {
            return GREEN;
        }
    }
}
