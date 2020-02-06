package com.codemonkeys9.budgeit.activities;

enum EntryTypeVisibility {
    Income,
    Expenses,
    Both;

    // Shows or hides income
    public EntryTypeVisibility toggleIncome() {
        switch(this) {
            case Income:
                throw new IllegalStateException("Can't hide everything!");
            case Expenses:
                return Both;
            case Both:
                return Expenses;
        }
        // This return is here because Java is too dumb to realize I already handled all the cases
        // and this code can't ever be executed:
        return null;
    }

    // Shows or hides expenses
    public EntryTypeVisibility toggleExpenses() {
        switch(this) {
            case Expenses:
                throw new IllegalStateException("Can't hide everything!");
            case Income:
                return Both;
            case Both:
                return Income;
        }
        // This return is here because Java is too dumb to realize I already handled all the cases
        // and this code can't ever be executed:
        return null;
    }

    public boolean isIncomeVisible() {
        return this == Income || this == Both;
    }

    public boolean areExpensesVisible() {
        return this == Expenses || this == Both;
    }
}
