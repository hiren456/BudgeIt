package com.codemonkeys9.budgeit;

import com.codemonkeys9.budgeit.Entry.Entry;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;

final class EntryAdapter extends ListAdapter<Entry, EntryAdapter.ViewHolder> {
    final static class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView amount;

        ViewHolder(View entryView) {
            super(entryView);
            description = entryView.findViewById(R.id.description);
            amount = entryView.findViewById(R.id.amount);
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
        viewHolder.description.setText(entry.getDetails());
        viewHolder.amount.setText(Integer.toString(entry.getAmount()));
    }

    public void updateEntries(List<Entry> newEntries) {
        submitList(newEntries);
    }
}
