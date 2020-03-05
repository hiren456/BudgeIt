package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.details.Details;

public class CategoryModifier implements UICategoryModifier {
    Database db;

    CategoryModifier (Database db)
    {
        this.db=db;
    }


    public void deleteCategory(int ID)
    {
        db.deleteCategory(ID);
    }


    public void changeGoal(int ID, Amount amount)
    {
        Category newCategory=db.selectCategoryByID(ID);
        if(newCategory!=null)
        {
            newCategory.modifyCategory(newCategory.getName(),amount,newCategory.getDateLastModified());
            db.updateCategory(newCategory);
        }
    }


    public void changeName(int ID, Details detail)
    {
        Category newCategory=db.selectCategoryByID(ID);
        if(newCategory!=null)
        {
            newCategory.modifyCategory(detail,newCategory.getGoal(),newCategory.getDateLastModified());
            db.updateCategory(newCategory);
        }
    }
}
