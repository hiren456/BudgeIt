package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;

class CategoryModifier implements UICategoryModifier {
    Database db;

    CategoryModifier (Database db)
    {
        this.db=db;
    }


    public void deleteCategory(int ID)
    {
        Category cat = getCat(ID);
        db.deleteCategory(ID);
    }


    public void changeGoal(int ID, Amount amount)
    {
        Category cat = getCat(ID);

        Category newCat =  cat.modifyCategory(cat.getName(),amount,cat.getDateLastModified());
        db.updateCategory(newCat);
    }


    public void changeName(int ID, Details detail)
    {
        Category cat = getCat(ID);

        Category newCat =  cat.modifyCategory(detail,cat.getGoal(),cat.getDateLastModified());
        db.updateCategory(newCat);
    }

    private Category getCat(int ID){
        Category ret = db.selectCategoryByID(ID);
        if(ret == null){
            throw new CategoryDoesNotExistException("Category with ID: " + ID + " does not exist.");
        }
        return ret;
    }
}
