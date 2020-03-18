package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifier;
import com.codemonkeys9.budgeit.logiclayer.uicategorymodifier.UICategoryModifierFactory;

import java.util.List;

public class CategoriesFragment extends Fragment {
    private CategoryAdapter categoryAdapter;

    private UICategoryFetcher categoryFetcher;
    private UICategoryModifier categoryModifier;
    private List<Category> categories;

    private boolean active = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recycler = v.findViewById(R.id.category_recycler);
        recycler.setAdapter(this.categoryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button newCategoryButton = v.findViewById(R.id.newCategoryButton);
        newCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewCategoryActivity();
            }
        });

        return v;
    }
    private void openNewCategoryActivity() {
        Intent i = new Intent(getContext(), NewCategoryActivity.class);
        startActivity(i);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        this.categoryModifier = UICategoryModifierFactory.createUICategoryModifier();

        CategoryList categoryList = categoryFetcher.fetchAllCategories();
        this.categories = categoryList.getReverseChrono();
        this.categoryAdapter = new CategoryAdapter(categories);
    }

    @Override
    public void onResume() {
        this.active = true;
        refreshList();
        super.onResume();
    }

    @Override
    public void onPause() {
        this.active = false;
        super.onPause();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // We will get context item events for all fragments in MainPager. We have to return false
        // in order for other fragments to have a chance to handle them.
        if(!this.active) return false;

        // Get index *within the currently-displayed list of categories*
        int categoryIndex = item.getGroupId();
        // Get actual, global entry ID
        int categoryId = categories.get(categoryIndex).getID();
        int buttonId = item.getItemId();

        switch(buttonId) {
            case R.id.action_delete:
                categoryModifier.deleteCategory(categoryId);
                break;
        }
        refreshList();
        return true;
    }

    private void refreshList() {
        CategoryList categoryList = this.categoryFetcher.fetchAllCategories();
        this.categories = categoryList.getReverseChrono();
        categoryAdapter.updateCategories(this.categories);
    }
}
