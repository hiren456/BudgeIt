package com.codemonkeys9.budgeit.logiclayer.uicategorycreator;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.amount.AmountFactory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.BudgetCategoryFactory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.dso.category.SavingsCategoryFactory;
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
    public void createSavingsCategory(Amount goal, Details name) {
        int catID = getID();
        SavingsCategory cat = SavingsCategoryFactory.createSavingsCategory(name, goal,catID);
        storeCat(cat);
    }

    @Override
    public void createSavingsCategory(String goal, String name) throws InvalidAmountException {
        Amount parsedGoal = AmountFactory.fromString(goal);
        Details parsedName = DetailsFactory.fromString(name);
        createSavingsCategory(parsedGoal,parsedName);
    }

    @Override
    public void createBudgetCategory(Amount goal, Details name) {
        int catID = getID();
        BudgetCategory cat = BudgetCategoryFactory.createBudgetCategory(name, goal,catID);
        storeCat(cat);
    }

    @Override
    public void createBudgetCategory(String goal, String name) throws InvalidAmountException {
        Amount parsedGoal = AmountFactory.fromString(goal);
        Details parsedName = DetailsFactory.fromString(name);
        createBudgetCategory(parsedGoal,parsedName);
    }

    private int getID(){
        return idManager.getDefaultID("Category");
    }

    private void storeCat(Category cat){
        db.insertCategory(cat);
    }
}
