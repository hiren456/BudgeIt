package com.codemonkeys9.budgeit.ui;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.category.BudgetCategory;
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;

import java.util.List;

final class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.ViewHolder> {
    final static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView amount;
        TextView date;

        ViewHolder(View entryView) {
            super(entryView);
            description = entryView.findViewById(R.id.description);
            amount = entryView.findViewById(R.id.amount);
            date = entryView.findViewById(R.id.date);
        }
    }

    private static final DiffUtil.ItemCallback<Category> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Category>() {
                @Override
                public boolean areItemsTheSame(Category oldEntry, Category newEntry) {
                    return oldEntry.getID() == newEntry.getID();
                }

                @Override
                public boolean areContentsTheSame(Category oldEntry, Category newEntry) {
                    return oldEntry.equals(newEntry);
                }
            };

    public CategoryAdapter(List<Category> entries) {
        super(DIFF_CALLBACK);
        submitList(entries);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View entryView = inflater.inflate(R.layout.timeline_entry, parent, false);
        return new ViewHolder(entryView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Category category = getItem(position);
        viewHolder.description.setText(category.getName().getValue());
        viewHolder.date.setText(category.getDateLastModified().getDisplay());

        // Decide which color to make the amount based on whether it is negative or positive
        // Colors are encoded in ARGB format, one byte (or two hex digits) per channel.
        int color;
        if(category instanceof BudgetCategory) {
            // Red
            color = 0xFFFF0000;
        } else {
            // Green
            color = 0xFF00AA00;
        }
        viewHolder.amount.setTextColor(color);
        viewHolder.amount.setText(category.getGoal().getDisplay());
    }

    public void updateCategories(List<Category> newCategories) {
        submitList(newCategories);
    }
}
