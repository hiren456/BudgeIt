package com.codemonkeys9.budgeit.logiclayer.uicategorycreator;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;

public interface UICategoryCreator {
    /*
    Create's and stores a SavingsCategory object from DSOs or Strings
     */
    void createSavingsCategory(Amount goal, Details name);
    void createSavingsCategory(String goal, String name)
            throws InvalidAmountException;

    /*
    Create's and stores a BudgetCategory object from DSOs or Strings
     */
    void createBudgetCategory(Amount goal, Details name);
    void createBudgetCategory(String goal, String name)
            throws InvalidAmountException;
}
