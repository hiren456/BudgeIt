package com.codemonkeys9.budgeit.ui;

enum CategoryVisibility {
    Savings,
    Budget,
    Both;

    // Shows or hides income
    public CategoryVisibility toggleSavings() {
        switch(this) {
            case Savings:
                throw new IllegalStateException("Can't hide everything!");
            case Budget:
                return Both;
            case Both:
                return Budget;
        }
        // This return is here because Java is too dumb to realize I already handled all the cases
        // and this code can't ever be executed:
        return null;
    }

    // Shows or hides expenses
    public CategoryVisibility toggleBudget() {
        switch(this) {
            case Budget:
                throw new IllegalStateException("Can't hide everything!");
            case Savings:
                return Both;
            case Both:
                return Savings;
        }
        // This return is here because Java is too dumb to realize I already handled all the cases
        // and this code can't ever be executed:
        return null;
    }

    public boolean isIncomeVisible() {
        return this == Savings || this == Both;
    }

    public boolean areExpensesVisible() {
        return this == Budget || this == Both;
    }
}

