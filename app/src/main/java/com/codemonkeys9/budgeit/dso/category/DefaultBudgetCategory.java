package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

class DefaultBudgetCategory extends DefaultCategory implements BudgetCategory {

    DefaultBudgetCategory(Details name, Amount goal, Date date,int id) {
        super(name, goal, id, date);
    }

    @Override
    public boolean equals(Category other) {
        boolean nameSame = this.name.equals(other.getName());
        boolean goalSame = this.goal.equals(other.getGoal());
        boolean dateSame = this.dateLastModified.equals(other.getDateLastModified());
        boolean idSame = this.id == other.getID();
        boolean typeSame = other instanceof BudgetCategory;

        return nameSame && goalSame
                && idSame && typeSame && dateSame;
    }

    @Override
    public Category modifyCategory(Details name, Amount goal,Date newDate) {
        Details newName = DetailsFactory.fromString(name.getValue());
        Amount newGoal = AmountFactory.fromInt(goal.getValue());

        return new DefaultBudgetCategory(newName,newGoal,newDate,this.id);
    }
}
