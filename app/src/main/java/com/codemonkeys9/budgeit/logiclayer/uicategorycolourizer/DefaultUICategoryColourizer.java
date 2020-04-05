package com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer;

import android.graphics.Color;

import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;

class DefaultUICategoryColourizer implements UICategoryColourizer {
    private static final int GREEN = Color.argb(0xFF, 0x00, 0xAA, 0x00);
    public int getAmountColour(Category category) {
        if(category instanceof BudgetCategory) {
            return Color.RED;
        } else {
            return GREEN;
        }
    }
}
