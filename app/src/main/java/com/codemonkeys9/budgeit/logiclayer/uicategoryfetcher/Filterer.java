package com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher;

import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.category.SavingsCategory;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Income;
import com.codemonkeys9.budgeit.dso.entry.Purchase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Filterer {

    public static void getSavings(List<Category> list) {
        ArrayList<Category> catToRemove = new ArrayList<Category>();

        Iterator<Category> iter = list.iterator();
        while(iter.hasNext()){

            Category curr = iter.next();
            if(curr instanceof BudgetCategory){

                catToRemove.add(curr);
            }
        }

        for( Category curr : catToRemove){

            list.remove(curr);
        }
    }

    public static void getBudget(List<Category> list) {
        ArrayList<Category> catToRemove = new ArrayList<Category>();

        Iterator<Category> iter = list.iterator();
        while(iter.hasNext()){

            Category curr = iter.next();
            if(curr instanceof SavingsCategory){

                catToRemove.add(curr);
            }
        }

        for( Category curr : catToRemove){

            list.remove(curr);
        }
    }
}
