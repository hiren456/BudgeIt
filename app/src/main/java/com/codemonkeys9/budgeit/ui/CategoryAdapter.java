package com.codemonkeys9.budgeit.ui;

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
import com.codemonkeys9.budgeit.dso.entry.RecurrencePeriod;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculator;
import com.codemonkeys9.budgeit.logiclayer.entrycalculator.EntryCalculatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer.UICategoryColourizer;
import com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer.UICategoryColourizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

import java.util.List;

final class CategoryAdapter extends ListAdapter<Category, RecyclerView.ViewHolder>{
    private OnCategoryListener onCategoryListener;

    private static final int PIE_CHART = 0;
    private static final int CATEGORY = 1;

    public interface OnCategoryListener{
        void onCategoryClick(int position);
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

    final static class PieChartViewHolder extends RecyclerView.ViewHolder {
        PieChartViewHolder(final View view) {
            super(view);
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
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int layout = -1;
        View view;
        switch(viewType) {
            case PIE_CHART:
                view = inflater.inflate(R.layout.pie_chart, parent, false);
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
        System.out.println("THIS HAPPENS");
        switch(getItemViewType(position)) {
            case PIE_CHART:
                break;
            case CATEGORY:
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
                break;
        }
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
