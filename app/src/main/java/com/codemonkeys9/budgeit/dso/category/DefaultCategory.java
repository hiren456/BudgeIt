package com.codemonkeys9.budgeit.dso.category;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.details.Details;

abstract class DefaultCategory implements Category{
    Date dateLastModified;
    Details name;
    Amount goal;
    int id;

    DefaultCategory(Details name, Amount goal, int id,Date dateLastModified){
        if(name == null){
            throw new NullPointerException("name is null");
        }
        if(goal == null){
            throw new NullPointerException("goal is null");
        }
        if(dateLastModified == null){
            throw new NullPointerException("dateLastModified is null");
        }

        this.dateLastModified = dateLastModified;
        this.name = name;
        this.goal = goal;
        this.id = id;
    }

    public String toString(){ return name.getValue(); }

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

    @Override
    public Date getDateLastModified() {
        return this.dateLastModified;
    }
}
