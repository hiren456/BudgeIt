package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;

public interface UICategoryModifier {
    //Delete category with categoryID "ID"
    void deleteCategory(int ID) throws  CategoryDoesNotExistException;
    //Changes the goal of a category
    void changeGoal(int ID, Amount amount) throws  CategoryDoesNotExistException;
    //changes the name of a category
    void changeName(int ID, Details detail) throws  CategoryDoesNotExistException;
}
