package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

public interface Category {
    Details getName();
    Amount getGoal();
    int getID();
    boolean equals(Category other);
    Date getDateLastModified();
    Category modifyCategory(Details name, Amount goal, Date newDate);
    String toString();
}
