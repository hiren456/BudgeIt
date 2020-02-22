package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.details.Details;

abstract class DefaultCategory implements Category{
    Details name;
    Amount goal;
    int id;

    DefaultCategory(Details name, Amount goal, int id){
        if(name == null){

            throw new NullPointerException();
        }
        if(goal == null){

            throw new NullPointerException();
        }

        this.name = name;
        this.goal = goal;
        this.id = id;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public Details getName() {
        return this.name;
    }

    @Override
    public Amount getGoal() {
        return this.goal;
    }
}
