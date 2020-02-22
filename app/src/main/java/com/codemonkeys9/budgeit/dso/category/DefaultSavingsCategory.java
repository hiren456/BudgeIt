package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;

class DefaultSavingsCategory extends DefaultCategory implements SavingsCategory {

    DefaultSavingsCategory(Details name, Amount goal, int id) {
        super(name, goal, id);
    }

    @Override
    public boolean equals(Category other) {
        boolean nameSame = this.name.equals(other.getName());
        boolean goalSame = this.goal.equals(other.getGoal());
        boolean idSame = this.id == other.getID();
        boolean typeSame = other instanceof SavingsCategory;

        return nameSame && goalSame
                && idSame && typeSame;
    }

    @Override
    public Category modifyCategory(Details name, Amount goal) {
        Details newName = DetailsFactory.fromString(name.getValue());
        Amount newGoal = AmountFactory.fromInt(goal.getValue());

        return new DefaultSavingsCategory(newName,newGoal,this.id);
    }
}
