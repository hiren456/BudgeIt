package com.codemonkeys9.budgeit.logiclayer.uicategorymodifier;

import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.details.Details;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;

public interface UICategoryModifier {
    //Delete category with categoryID "ID" from the database and returns true on success
    boolean deleteCategory(int ID) throws  CategoryDoesNotExistException;
    //Changes the goal of a category and returns true on success
    boolean changeGoal(int ID, Amount amount) throws  CategoryDoesNotExistException;
    //changes the name of a category and returns true on success
    boolean changeName(int ID, Details detail) throws  CategoryDoesNotExistException;
}
