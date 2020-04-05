package com.codemonkeys9.budgeit.logiclayer.uientrycategorizer;

import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.exceptions.CategoryDoesNotExistException;
import com.codemonkeys9.budgeit.exceptions.EntryDoesNotExistException;

class EntryCategorizer implements UIEntryCategorizer {
    Database db;

    EntryCategorizer(Database db){
        this.db = db;
    }

    @Override
    public void categorizeEntry(BaseEntry entry, Category category) throws EntryDoesNotExistException, CategoryDoesNotExistException {
       categorizeEntry(entry.getEntryID(),category.getID());
    }

    @Override
    public void categorizeEntry(int entryID, int categoryID) throws EntryDoesNotExistException, CategoryDoesNotExistException {
        //Find Objects
        Category cat = fetchCategory(categoryID);
        BaseEntry entry = fetchEntry(entryID);

        Category newCat = cat.modifyCategory(cat.getName(),cat.getGoal()
                , DateFactory.fromString("now"));
        BaseEntry newEntry = entry.changeCategory(categoryID);
        db.updateDefaultEntry(newEntry);
        db.updateCategory(newCat);
    }

    private BaseEntry fetchEntry(int id){
        BaseEntry entry = this.db.selectDefaultEntryByID(id);
        if(entry == null){
            throw new EntryDoesNotExistException("Entry with id " +id+" does not exist");
        }
        return entry;
    }

    private Category fetchCategory(int id){
        Category cat = this.db.selectCategoryByID(id);
        if(cat == null){
            throw new CategoryDoesNotExistException("Category with id " +id+" does not exist");
        }
        return cat;
    }
}
