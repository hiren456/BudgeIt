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
import com.codemonkeys9.budgeit.dso.category.Category;
import com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer.UICategoryColourizer;
import com.codemonkeys9.budgeit.logiclayer.uicategorycolourizer.UICategoryColourizerFactory;

import java.util.List;

final class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.ViewHolder>{
    private OnCategoryListener onCategoryListener;

    public interface OnCategoryListener{
        void onCategoryClick(int position);
    }

    final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {
        TextView description;
        TextView amount;
        TextView date;
        OnCategoryListener onCategoryListener;

        ViewHolder(final View entryView, OnCategoryListener onCategoryListener) {
            super(entryView);
            description = entryView.findViewById(R.id.description);
            amount = entryView.findViewById(R.id.amount);
            date = entryView.findViewById(R.id.date);
            entryView.setOnCreateContextMenuListener(this);
            entryView.setOnClickListener(this);
            this.onCategoryListener = onCategoryListener;
            entryView.setOnClickListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), R.id.action_delete, 0, "Delete");
            menu.add(this.getAdapterPosition(), R.id.modify_amount, 0, "Modify Amount");
            menu.add(this.getAdapterPosition(), R.id.modify_description, 0, "Modify Description");
        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
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

    public CategoryAdapter(List<Category> cats, OnCategoryListener onCategoryListener) {
        super(DIFF_CALLBACK);
        submitList(cats);
        this.onCategoryListener = onCategoryListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View entryView = inflater.inflate(R.layout.timeline_entry, parent, false);
        return new ViewHolder(entryView, onCategoryListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Category category = getItem(position);
        viewHolder.description.setText(category.getName().getValue());
        viewHolder.date.setText(category.getDateLastModified().getDisplay());

        UICategoryColourizer colourizer = UICategoryColourizerFactory.createUICategoryColourizer();
        viewHolder.amount.setTextColor(colourizer.getAmountColour(category));
        viewHolder.amount.setText(category.getGoal().getDisplay());
    }

    public void updateCategories(List<Category> newCategories) {
        submitList(newCategories);
    }
}
