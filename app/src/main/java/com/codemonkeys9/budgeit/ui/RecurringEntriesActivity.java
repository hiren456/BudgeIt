package com.codemonkeys9.budgeit.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.entry.BaseEntry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;

import java.util.List;

public class RecurringEntriesActivity extends AppCompatActivity {
    UIEntryFetcher entryFetcher;
    private List<BaseEntry> entries;
    EntryAdapter entryAdapter;
    boolean mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_entries);
        entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        String mode = getIntent().getStringExtra("mode");
        this.mode = mode.equals("income");

        EntryList entryList;
        if(this.mode) entryList = entryFetcher.fetchAllRecurringIncomes();
        else entryList = entryFetcher.fetchAllRecurringExpenses();
        entries = entryList.getChrono();
        entryAdapter = new EntryAdapter(entries);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recurring_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(entryAdapter);
    }

    @Override
    public void onResume() {
        refreshTimeline();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void refreshTimeline() {
        EntryList entryList;
        if(mode) entryList = entryFetcher.fetchAllRecurringIncomes();
        else entryList = entryFetcher.fetchAllRecurringExpenses();
        this.entries = entryList.getChrono();
        entryAdapter.updateEntries(this.entries);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get index *within the currently-displayed list of entries*
        int entryIndex = item.getGroupId();
        // Get actual, global entry ID
        int entryId = entries.get(entryIndex).getEntryID();
        int buttonId = item.getItemId();
        entryAdapter.onContextItemSelected(this, entryId, buttonId);
        refreshTimeline();
        return true;
    }
}
