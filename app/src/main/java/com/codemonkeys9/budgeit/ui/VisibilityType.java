package com.codemonkeys9.budgeit.ui;

enum VisibilityType {
    Income,
    Expenses,
    Categories,
    Both;

    // Shows or hides income
    public VisibilityType toggleIncome() {
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
    public VisibilityType toggleExpenses() {
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

    public VisibilityType toggleCategories(){
        switch (this){
            case Income:
            case Expenses:
            case Both:
                return Categories;
            case Categories:
                throw new IllegalStateException("Already on Categories");
        }
        return null;
    }

    public boolean isIncomeVisible() {
        return this == Income || this == Both;
    }

    public boolean areExpensesVisible() {
        return this == Expenses || this == Both;
    }

    public boolean areCategoriesVisible() {
        return this == Categories;
    }


}
