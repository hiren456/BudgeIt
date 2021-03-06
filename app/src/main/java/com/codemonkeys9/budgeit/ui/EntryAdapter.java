package com.codemonkeys9.budgeit.ui;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entry.Purchase;
import com.codemonkeys9.budgeit.logiclayer.uientrycolourizer.UIEntryColourizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycolourizer.UIEntryColourizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import java.util.List;

final class EntryAdapter extends ListAdapter<BaseEntry, EntryAdapter.ViewHolder> {
    final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView description;
        TextView amount;
        TextView date;

        /// Whether the entry is possible to flag; aka whether it is a purchase
        boolean flaggable;
        /// Whether the entry is currently flagged (also implies it's a purchase)
        boolean flagged;

        ViewHolder(View entryView) {
            super(entryView);
            description = entryView.findViewById(R.id.description);
            amount = entryView.findViewById(R.id.amount);
            date = entryView.findViewById(R.id.date);
            entryView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(), R.id.action_delete, 0, "Delete");
            menu.add(this.getAdapterPosition(), R.id.modify_category, 0, "Change Category");
            menu.add(this.getAdapterPosition(), R.id.modify_amount, 0, "Modify Amount");
            menu.add(this.getAdapterPosition(), R.id.modify_date, 0, "Modify Date");
            menu.add(this.getAdapterPosition(), R.id.modify_description, 0, "Modify Description");
            if(flagged) {
                menu.add(this.getAdapterPosition(), R.id.action_unflag, 0, "Unflag");
            } else if(flaggable) {
                menu.add(this.getAdapterPosition(), R.id.action_flag, 0, "Flag");
            }
        }
    }

    private static final DiffUtil.ItemCallback<BaseEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BaseEntry>() {
                @Override
                public boolean areItemsTheSame(BaseEntry oldEntry, BaseEntry newEntry) {
                    return oldEntry.getEntryID() == newEntry.getEntryID();
                }

                @Override
                public boolean areContentsTheSame(BaseEntry oldEntry, BaseEntry newEntry) {
                    return oldEntry.equals(newEntry);
                }
            };

    public EntryAdapter(List<BaseEntry> entries) {
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
        BaseEntry entry = getItem(position);

        viewHolder.flaggable = entry instanceof Purchase;
        viewHolder.flagged = viewHolder.flaggable && ((Purchase)entry).flagged();

        viewHolder.date.setText(entry.getDate().getDisplay());

        UIEntryColourizer colourizer = UIEntryColourizerFactory.createUIEntryColourizer();
        viewHolder.description.setTextColor(colourizer.getDescriptionColour(entry));
        viewHolder.description.setText(entry.getDetails().getValue());

        viewHolder.amount.setTextColor(colourizer.getAmountColour(entry));
        viewHolder.amount.setText(entry.getAmount().getDisplay());
    }

    public void updateEntries(List<BaseEntry> newEntries) {
        submitList(newEntries);
    }

    public void onContextItemSelected(Context context, int entryId, int buttonId){
        UIEntryManager entryManager = UIEntryManagerFactory.createUIEntryManager();

        Intent i;
        switch(buttonId) {
            case R.id.action_delete:
                entryManager.deleteEntry(entryId);
                break;
            case R.id.action_flag:
                entryManager.flagPurchase(entryId, true);
                break;
            case R.id.action_unflag:
                entryManager.flagPurchase(entryId, false);
                break;
            case R.id.modify_amount:
                i = new Intent(context , ModifyEntryAmountActivity.class);
                i.putExtra("entryId",entryId);
                context.startActivity(i);
                break;
            case R.id.modify_description:
                i = new Intent(context , ModifyEntryDescriptionActivity.class);
                i.putExtra("entryId",entryId);
                context.startActivity(i);
                break;
            case R.id.modify_date:
                i = new Intent(context , ModifyEntryDateActivity.class);
                i.putExtra("entryId",entryId);
                context.startActivity(i);
                break;
            case R.id.modify_category:
                i = new Intent(context , ChangeEntryCategoryActivity.class);
                i.putExtra("entryId",entryId);
                context.startActivity(i);
                break;
        }
    }


}
