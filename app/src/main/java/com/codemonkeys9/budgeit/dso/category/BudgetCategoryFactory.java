package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;

public class BudgetCategoryFactory {
    public static BudgetCategory createBudgetCategory(Details name, Amount goal,int id){
        return new DefaultBudgetCategory(name,goal,id);
    }
}
