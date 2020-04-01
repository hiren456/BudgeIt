package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
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

import static android.app.Activity.RESULT_OK;

public class CategoriesFragment extends Fragment implements CategoryAdapter.OnCategoryListener {
    private CategoryAdapter categoryAdapter;

    // Request codes for activities that need to return data
    private static int NEW_CATEGORY = 0;

    //visibility variables
    private CategoryVisibility visibility = CategoryVisibility.Both;
    private MenuItem budgetToggle;
    private MenuItem savingsToggle;

    private UICategoryFetcher categoryFetcher;
    private UICategoryModifier categoryModifier;
    private CategoryList categories;

    RecyclerView recycler;

    // This variable is updated when a new category is created to its ID. This is used so we can
    // scroll to its position. This is necessary because we use ListAdapter and DiffUtil for diffing
    // lists, and that is done asynchronously on a background thread. So we have to register an
    // observer and perform the scrolling there.
    Integer newID = null;
    final RecyclerView.AdapterDataObserver recyclerViewObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if(newID != null) {
                scrollToID(newID);
                newID = null;
            }
        }
    };

    private boolean active = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recycler = v.findViewById(R.id.category_recycler);
        recycler.setAdapter(this.categoryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);

        this.categoryAdapter.registerAdapterDataObserver(this.recyclerViewObserver);

        Button newCategoryButton = v.findViewById(R.id.newCategoryButton);
        newCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewCategoryActivity();
            }
        });

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.categoryAdapter.unregisterAdapterDataObserver(this.recyclerViewObserver);
    }

    private void openNewCategoryActivity() {
        Intent i = new Intent(getContext(), NewCategoryActivity.class);
        startActivityForResult(i, NEW_CATEGORY);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        this.categoryModifier = UICategoryModifierFactory.createUICategoryModifier();

        this.categories = categoryFetcher.fetchAllCategories();
        this.categoryAdapter = new CategoryAdapter(this.categories.getReverseChrono(), this);
    }

    @Override
    public void onResume() {
        this.active = true;
        refreshList();
        this.recycler = getView().findViewById(R.id.category_recycler);
        super.onResume();
    }

    @Override
    public void onPause() {
        this.active = false;
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_categories, menu);
        savingsToggle = menu.findItem(R.id.action_toggle_savings);
        budgetToggle = menu.findItem(R.id.action_toggle_budget);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        savingsToggle.setVisible(true);
        budgetToggle.setVisible(true);

        if(visibility.areSavingsVisible()) {
            savingsToggle.setTitle(getString(R.string.action_hide_savings));
            // Don't allow the user to hide both types of entries
            if(!visibility.areBudgetsVisible()) {
                savingsToggle.setVisible(false);
            }
        } else {
            savingsToggle.setTitle(getString(R.string.action_show_savings));
        }

        if(visibility.areBudgetsVisible()) {
            budgetToggle.setTitle(getString(R.string.action_hide_budget));
            // Don't allow the user to hide both types of entries
            if(!visibility.areSavingsVisible()) {
                budgetToggle.setVisible(false);
            }
        } else {
            budgetToggle.setTitle(getString(R.string.action_show_budget));
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_toggle_savings) {
            visibility = visibility.toggleSavings();
        } else if (id == R.id.action_toggle_budget) {
            visibility = visibility.toggleBudget();
        }
        if(id == R.id.action_toggle_savings|| id == R.id.action_toggle_budget) {
            refreshList();
            // Make sure Android updates the options menu next time it gets displayed
            getActivity().invalidateOptionsMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // We will get context item events for all fragments in MainPager. We have to return false
        // in order for other fragments to have a chance to handle them.
        if(!this.active) return false;

        // Get index *within the currently-displayed list of categories*
        int categoryIndex = item.getGroupId();
        // Get actual, global entry ID
        int categoryId = categories.getInReverseChrono(categoryIndex).getID();
        int buttonId = item.getItemId();

        Intent i;
        switch(buttonId) {
            case R.id.action_delete:
                categoryModifier.deleteCategory(categoryId);
                break;
            case R.id.modify_amount:
                i = new Intent(getContext(), ModifyCategoryAmountActivity.class);
                i.putExtra("catId",categoryId);
                startActivity(i);
                break;
            case R.id.modify_description:
                i = new Intent(getContext() , ModifyCategoryDescriptionActivity.class);
                i.putExtra("catId",categoryId);
                startActivity(i);
                break;
        }
        refreshList();
        return true;
    }

    private void refreshList() {
        switch (visibility){
            case Savings:
                this.categories = categoryFetcher.fetchAllSavingsCategories();
                break;
            case Budget:
                this.categories = categoryFetcher.fetchAllBudgetCategories();
                break;
            case Both:
                this.categories = categoryFetcher.fetchAllCategories();
                break;
        }
        categoryAdapter.updateCategories(this.categories.getReverseChrono());
    }

    @Override
    public void onCategoryClick(int position) {
        Category c = categories.getInReverseChrono(position);
        openCategory(c.getID());
    }

    private void openCategory(int catID){
        Intent i = new Intent(getContext(), CategoryViewActivity.class);
        i.putExtra("catID", catID);
        startActivity(i);
    }

    public void scrollToID(int catID) {
        int target = this.categories.getReverseChronoIndexOfCategoryWithID(catID);
        recycler.smoothScrollToPosition(target);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == NEW_CATEGORY) {
                if(data.hasExtra("newly_created_category_id")) {
                    refreshList();
                    Bundle extras = data.getExtras();
                    int catID = extras.getInt("newly_created_category_id");
                    this.newID = catID;
                }
            }
        }
    }
}
