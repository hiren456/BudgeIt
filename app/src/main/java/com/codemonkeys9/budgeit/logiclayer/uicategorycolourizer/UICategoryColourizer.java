package com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer;

import com.codemonkeys9.budgeit.dso.category.Category;

public interface UICategoryColourizer {
    /*
    Get colour of the amount field encoded in ARGB format, one byte (or two hex digits) per channel
     */
    int getAmountColour(Category category);

    /*
    Get colour of the monthly progress bar encoded in ARGB format, one byte (or two hex digits) per channel
     */
    int getProgressBarColour(Category category, int absSum);
}
