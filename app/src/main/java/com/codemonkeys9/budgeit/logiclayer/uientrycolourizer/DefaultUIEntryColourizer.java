package com.codemonkeys9.budgeit.logiclayer.uientrycolourizer;

import android.graphics.Color;

import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.dso.entry.RecurringPurchase;

class DefaultUIEntryColourizer implements UIEntryColourizer {
    private static final int GREEN = Color.argb(0xFF, 0x00, 0xAA, 00);

    public int getDescriptionColour(BaseEntry entry) {
        if(entry instanceof Purchase) {
            Purchase purchase = (Purchase)entry;
            if(purchase.flagged()) {
                return Color.RED;
            }
        }
        if(entry instanceof RecurringPurchase) {
            RecurringPurchase purchase = (RecurringPurchase)entry;
            if(purchase.flagged()) {
                return Color.RED;
            }
        }
        return Color.BLACK;
    }

    public int getAmountColour(BaseEntry entry) {
        if(entry instanceof Purchase || entry instanceof RecurringPurchase) {
            return Color.RED;
        } else {
            return GREEN;
        }
    }
}
