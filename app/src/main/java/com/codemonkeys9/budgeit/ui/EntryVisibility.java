package com.codemonkeys9.budgeit.ui;

enum EntryVisibility {
    Income,
    Expenses,
    RecurringIncome,
    RecurringExpenses,
    All;

    // Shows or hides income
    public EntryVisibility toggleIncome() {
        switch(this) {
            case Income:
                throw new IllegalStateException("Can't hide everything!");
            case Expenses:
                return All;
            case All:
                return Expenses;
        }
        // This return is here because Java is too dumb to realize I already handled all the cases
        // and this code can't ever be executed:
        return null;
    }

    // Shows or hides expenses
    public EntryVisibility toggleExpenses() {
        switch(this) {
            case Expenses:
                throw new IllegalStateException("Can't hide everything!");
            case Income:
                return All;
            case All:
                return Income;
        }
        // This return is here because Java is too dumb to realize I already handled all the cases
        // and this code can't ever be executed:
        return null;
    }

    public EntryVisibility getRecurringIncome(){
        return RecurringIncome;
    }
    public EntryVisibility getRecurringExpenses(){
        return RecurringExpenses;
    }
    public EntryVisibility getAll(){
        return All;
    }

    public boolean isIncomeVisible() {
        return this == Income || this == All;
    }

    public boolean areExpensesVisible() {
        return this == Expenses || this == All;
    }

    public boolean isOnlyRecurringVisible() { return this == RecurringIncome || this == RecurringExpenses; }

}
