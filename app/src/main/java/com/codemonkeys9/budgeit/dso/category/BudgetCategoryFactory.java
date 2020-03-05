package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public class BudgetCategoryFactory {
    public static BudgetCategory createBudgetCategory(Details name, Amount goal, Date date,int id){
        return new DefaultBudgetCategory(name,goal,date,id);
    }
}
