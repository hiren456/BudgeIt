package com.codemonkeys9.budgeit.logiclayer.uicategorycreator;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
import com.codemonkeys9.budgeit.dso.date.Date;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.dso.details.DetailsFactory;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;

class CategoryCreator implements UICategoryCreator {
    Database db;
    IDManager idManager;

    CategoryCreator(Database db,IDManager idManager){
        this.db = db;
        this.idManager = idManager;
    }

    @Override
    public int createSavingsCategory(Amount goal, Details name) {
        int catID = getID();
        Date today = DateFactory.fromString("now");
        SavingsCategory cat = SavingsCategoryFactory.createSavingsCategory(name, goal,today,catID);
        storeCat(cat);
        return catID;
    }

    @Override
    public int createSavingsCategory(String goal, String name) throws InvalidAmountException {
        Amount parsedGoal = AmountFactory.fromString(goal);
        Details parsedName = DetailsFactory.fromString(name);
        return createSavingsCategory(parsedGoal,parsedName);
    }

    @Override
    public int createBudgetCategory(Amount goal, Details name) {
        int catID = getID();
        Date today = DateFactory.fromString("now");
        BudgetCategory cat = BudgetCategoryFactory.createBudgetCategory(name, goal,today,catID);
        storeCat(cat);
        return catID;
    }

    @Override
    public int createBudgetCategory(String goal, String name) throws InvalidAmountException {
        Amount parsedGoal = AmountFactory.fromString(goal);
        Details parsedName = DetailsFactory.fromString(name);
        return createBudgetCategory(parsedGoal,parsedName);
    }

    private int getID(){
        return idManager.getDefaultID("Category");
    }

    private void storeCat(Category cat){
        db.insertCategory(cat);
    }
}
