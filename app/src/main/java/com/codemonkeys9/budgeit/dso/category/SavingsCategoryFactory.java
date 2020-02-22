package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;

public class SavingsCategoryFactory {
    public static SavingsCategory createSavingsCategory(Details name, Amount goal,int id){
        return new DefaultSavingsCategory(name,goal,id);
    }
}
