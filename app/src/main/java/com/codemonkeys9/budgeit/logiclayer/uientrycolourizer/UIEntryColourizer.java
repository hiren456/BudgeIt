package com.codemonkeys9.budgeit.logiclayer.uientrycolourizer;

import com.codemonkeys9.budgeit.dso.entry.Entry;

public interface UIEntryColourizer {
    /*
    Get colour of the description field encoded in ARGB format, one byte (or two hex digits) per channel
     */
    int getDescriptionColour(Entry entry);

    /*
    Get colour of the amount field encoded in ARGB format, one byte (or two hex digits) per channel
     */
    int getAmountColour(Entry entry);
}
