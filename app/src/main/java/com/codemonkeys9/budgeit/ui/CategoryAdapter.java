package com.codemonkeys9.budgeit.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.amount.Amount;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer.UICategoryColourizer;
import com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer.UICategoryColourizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.dso.categorylist.CategoryList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

final class CategoryAdapter extends ListAdapter<Category, RecyclerView.ViewHolder>{
    private OnCategoryListener onCategoryListener;
    // Completely arbitrary. Just used to consistently seed the RNG for the colours of the pie chart
    // slices. Otherwise, they would change colour every time you saw them.
    private static final long BUDGET_SEED = 54063;
    private static final long SAVINGS_SEED = 795774;

    private static final int PIE_CHART = 0;
    private static final int CATEGORY = 1;

    public interface OnCategoryListener{
        void onCategoryClick(int position);
    }

    UICategoryFetcher categoryFetcher;

    final static class PieChartViewHolder extends RecyclerView.ViewHolder {
        PieChartView budgetPie, savingsPie;
        PieChartViewHolder(final View view) {
            super(view);
            this.budgetPie = view.findViewById(R.id.budget_pie);
            this.savingsPie = view.findViewById(R.id.savings_pie);
        }
    }

    final static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {
        TextView description;
        TextView amountGoal;
        TextView date;
        TextView amountSum;
        ProgressBar monthlyProgressBar;
        OnCategoryListener onCategoryListener;

        CategoryViewHolder(final View view, OnCategoryListener onCategoryListener) {
            super(view);

            description = view.findViewById(R.id.description);
            amountGoal = view.findViewById(R.id.amount_goal);
            date = view.findViewById(R.id.date);
            amountSum = view.findViewById(R.id.amount_sum);
            monthlyProgressBar = view.findViewById(R.id.monthlyProgressBar);
            view.setOnCreateContextMenuListener(this);
            this.onCategoryListener = onCategoryListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getCategoryPosition(), R.id.action_delete, 0, "Delete");
            menu.add(getCategoryPosition(), R.id.modify_amount, 0, "Modify Amount");
            menu.add(getCategoryPosition(), R.id.modify_description, 0, "Modify Description");
        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getCategoryPosition());
        }

        public int getCategoryPosition() {
            return getAdapterPosition() - 1;
        }
    }

    private static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Category>() {
                @Override
                public boolean areItemsTheSame(Category oldEntry, Category newEntry) {
                    if(oldEntry == null) return newEntry == null;
                    if(newEntry == null) return false;
                    return oldEntry.getID() == newEntry.getID();
                }

                @Override
                public boolean areContentsTheSame(Category oldEntry, Category newEntry) {
                    if(oldEntry == null) return newEntry == null;
                    if(newEntry == null) return false;
                    return oldEntry.equals(newEntry);
                }
            };

    public CategoryAdapter(List<Category> cats, OnCategoryListener onCategoryListener) {
        super(DIFF_CALLBACK);
        cats.add(0, null);
        submitList(cats);
        this.onCategoryListener = onCategoryListener;
        this.categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch(viewType) {
            case PIE_CHART:
                view = inflater.inflate(R.layout.pie_charts, parent, false);
                return new PieChartViewHolder(view);
            case CATEGORY:
                view = inflater.inflate(R.layout.timeline_category, parent, false);
                return new CategoryViewHolder(view, onCategoryListener);
        }
        // Unreachable
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return PIE_CHART;
        } else {
            return CATEGORY;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder baseViewHolder, int position) {
        switch(getItemViewType(position)) {
            case PIE_CHART: {
                PieChartViewHolder viewHolder = (PieChartViewHolder) baseViewHolder;
                PieChartData budgetData = pieData(categoryFetcher.fetchAllBudgetCategories(), "Budget", BUDGET_SEED);
                PieChartData savingsData = pieData(categoryFetcher.fetchAllSavingsCategories(), "Savings", SAVINGS_SEED);
                viewHolder.budgetPie.setPieChartData(budgetData);
                viewHolder.savingsPie.setPieChartData(savingsData);
            } break;
            case CATEGORY: {
                CategoryViewHolder viewHolder = (CategoryViewHolder) baseViewHolder;
                Category category = getItem(position);
                viewHolder.description.setText(category.getName().getValue());
                viewHolder.date.setText(category.getDateLastModified().getDisplay());
                Amount sum = getCategorySumThisMonth(category);
                viewHolder.amountSum.setText("this month: " + convertCategorySumToString(sum) + " / ");
                UICategoryColourizer colourizer = UICategoryColourizerFactory.createUICategoryColourizer();
                viewHolder.amountGoal.setTextColor(colourizer.getAmountColour(category));
                viewHolder.amountGoal.setText(category.getGoal().getDisplay());

                int absSum = Math.abs(sum.getValue());
                int progressBarColour = colourizer.getProgressBarColour(category, absSum);
                Drawable progressDrawable = viewHolder.monthlyProgressBar.getProgressDrawable().mutate();
                progressDrawable.setColorFilter(progressBarColour, PorterDuff.Mode.MULTIPLY);
                viewHolder.monthlyProgressBar.setProgressDrawable(progressDrawable);
                viewHolder.monthlyProgressBar.setMax(category.getGoal().getValue());
                viewHolder.monthlyProgressBar.setProgress(absSum);
            } break;
        }
    }

    private PieChartData pieData(CategoryList list, String label, long seed) {
        List<Category> cats = list.getChrono();
        List<SliceValue> slices = new ArrayList<>();
        Random rand = new Random(seed);
        for(Category cat: cats) {
            int absSum = Math.abs(getCategorySumThisMonth(cat).getValue());
            if(absSum == 0) continue;
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            SliceValue slice = new SliceValue(absSum, Color.argb(0xFF, r, g, b));
            slice.setLabel(cat.getName().getValue());
            slices.add(slice);
        }

        PieChartData data = new PieChartData(slices);
        data.setHasLabels(true);
        data.setHasCenterCircle(true);
        data.setCenterText1(label);
        data.setHasLabelsOutside(false);
        data.setCenterText1FontSize(15);

        return data;
    }

    private String convertCategorySumToString(Amount sum) {
        String result = sum.getAbsoluteValueDisplay();
        if(result.equals(".00")) return "0";
        else return result;
    }

    private Amount getCategorySumThisMonth(Category c){
        UIEntryFetcher entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        EntryList entryList = entryFetcher.fetchEntrysInCategoryThisMonth(c.getID());

        EntryCalculator entryCalculator = EntryCalculatorFactory.createEntryCalculator();
        return entryCalculator.sumEntryList(entryList);
    }

    public void updateCategories(List<Category> newCategories) {
        newCategories.add(0, null);
        submitList(newCategories);
    }
}
