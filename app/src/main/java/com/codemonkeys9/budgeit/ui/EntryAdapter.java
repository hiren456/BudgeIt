package com.codemonkeys9.budgeit.ui;

import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.entry.Purchase;

import java.util.List;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;

final class EntryAdapter extends ListAdapter<Entry, EntryAdapter.ViewHolder> {
    final static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView description;
        TextView amount;
        TextView date;

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
            menu.add(this.getAdapterPosition(), R.id.action_flag, 0, "Flag");
        }
    }

    private static final DiffUtil.ItemCallback<Entry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Entry>() {
                @Override
                public boolean areItemsTheSame(Entry oldEntry, Entry newEntry) {
                    return oldEntry.getEntryID() == newEntry.getEntryID();
                }

                @Override
                public boolean areContentsTheSame(Entry oldEntry, Entry newEntry) {
                    return oldEntry.equals(newEntry);
                }
            };

    public EntryAdapter(List<Entry> entries) {
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
        Entry entry = getItem(position);
        viewHolder.description.setText(entry.getDetails().getValue());
        viewHolder.date.setText(entry.getDate().getDisplay());

        // Decide which color to make the amount based on whether it is negative or positive
        // Colors are encoded in ARGB format, one byte (or two hex digits) per channel.
        int color;
        if(entry instanceof Purchase) {
            // Red
            color = 0xFFFF0000;
        } else {
            // Green
            color = 0xFF00AA00;
        }
        viewHolder.amount.setTextColor(color);
        viewHolder.amount.setText(entry.getAmount().getDisplay());
    }

    public void updateEntries(List<Entry> newEntries) {
        submitList(newEntries);
    }
}
