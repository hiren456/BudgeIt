package com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer;

import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;

class DefaultUICategoryColourizer implements UICategoryColourizer {
    private static final int RED   = 0xFFFF0000;
    private static final int GREEN = 0xFF00AA00;

    public int getAmountColour(Category category) {
        if(category instanceof BudgetCategory) {
            return RED;
        } else {
            return GREEN;
        }
    }
}
