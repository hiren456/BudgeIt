package com.codemonkeys9.budgeit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.uicalculator.UICalculator;
import com.codemonkeys9.budgeit.logiclayer.uicalculator.UICalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import java.util.List;

public class CategoriesFragment extends Fragment {
    private CategoryAdapter categoryAdapter;

    private UICategoryFetcher categoryFetcher;
    private List<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recycler = v.findViewById(R.id.recycler);
        recycler.setAdapter(this.categoryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();

        CategoryList categoryList = categoryFetcher.fetchAllCategories();
        this.categories = categoryList.getReverseChrono();
        this.categoryAdapter = new CategoryAdapter(categories);
    }

    @Override
    public void onResume() {
        refreshList();
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get index *within the currently-displayed list of categories*
        int categoryIndex = item.getGroupId();
        // Get actual, global entry ID
        int categoryId = categories.get(categoryIndex).getID();
        int buttonId = item.getItemId();
        switch(buttonId) {

        }
        return true;
    }

    private void refreshList() {
        CategoryList categoryList = this.categoryFetcher.fetchAllCategories();
        this.categories = categoryList.getReverseChrono();
        categoryAdapter.updateCategories(this.categories);
    }
}
