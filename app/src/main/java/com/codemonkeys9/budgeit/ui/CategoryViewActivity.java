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

public class CategoryViewActivity extends AppCompatActivity {

    int catID;
    UIEntryFetcher entryFetcher;
    private List<BaseEntry> entries;
    EntryAdapter entryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        catID = getIntent().getIntExtra("catID", 0);

        entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        EntryList entryList = entryFetcher.fetchEntrysInCategory(catID);
        entries = entryList.getReverseChrono();
        entryAdapter = new EntryAdapter(entries);
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.category_entry_recycler);
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
        EntryList entryList = entryFetcher.fetchEntrysInCategory(catID);
        this.entries = entryList.getReverseChrono();
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
