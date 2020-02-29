package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EntryAdapter entryAdapter;
    private EntryTypeVisibility visibility = EntryTypeVisibility.Both;
    private MenuItem incomeToggle;
    private MenuItem expensesToggle;
    private MenuItem dateFilterToggle;

    private String startDate = "past";
    private String endDate = "now";
    private boolean hasDateFilter = false;

    UIEntryManager entryManager;
    UIEntryFetcher entryFetcher;
    List<Entry> entries;

    private static final int DATE_RANGE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This is necessary for LocalDate to work with
        // API < 23
        AndroidThreeTen.init(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.budgeit_logo);
        setSupportActionBar(toolbar);

        Button newEntryButton = findViewById(R.id.newEntryButton);
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewEntryActivity();
            }
        });

        DatabaseHolder.init();
        this.entryManager = UIEntryManagerFactory.createUIEntryManager();
        this.entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();

        EntryList entryList = entryFetcher.fetchAllEntrys();
        this.entries = entryList.getReverseChrono();

        // Add fake data if there's no data in the DB already
        if(entries.isEmpty()) {
            entryManager.createEntry("60", "Half-Life: Alyx Pre-order", "2019-12-01",true);
            ADD_FAKE_DATA:
            for(int year = 2018; year <= 2020; year++) {
                for(int month = 1; month <= 12; month++) {
                    if(year == 2020 && month > 2) break ADD_FAKE_DATA;
                    // ensures that month has two digits
                    String monthString;
                    if (month < 10) {
                        monthString = "0" + month;
                    } else {
                        monthString = "" + month;
                    }

                    // Gas every week-ish
                    for (int j = 0; j < 4; j++) {
                        int day = j * 7 + 1;
                        // ensures that a day has two digits
                        String dayString;
                        if (day < 10) {
                            dayString = "0" + day;
                        } else {
                            dayString = "" + day;
                        }

                        entryManager.createEntry("50", "Gas", year + "-" + monthString + "-" + dayString, true);
                    }
                    // Paycheck every two weeks-ish
                    entryManager.createEntry("1000", "Paycheck", year + "-" + monthString + "-01", false);
                    entryManager.createEntry("1000", "Paycheck", year + "-" + monthString + "-15", false);

                    entryManager.createEntry(
                            "120",
                            "Something with an extremely, exceptionally, extraordinarily, staggeringly, shockingly, positively supercalifragilisticexpialidociously long description",
                            year + "-" + monthString + "-13",
                            true
                    );
                }
            }
            entryList = entryFetcher.fetchAllEntrys();
            entries = entryList.getReverseChrono();
        }

        this.entryAdapter = new EntryAdapter(entries);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setAdapter(this.entryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Get index *within the currently-displayed list of entries*
        int entryIndex = item.getGroupId();
        // Get actual, global entry ID
        int entryId = entries.get(entryIndex).getEntryID();
        int buttonId = item.getItemId();
        switch(buttonId) {
            case R.id.action_delete:
                Toast.makeText(this, "Deletion not yet implemented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_flag:
                // TODO: This will throw an exception if the selected entry is not a purchase.
                //       The best way to avoid this would be to not show the flag button on income
                //       items.
                entryManager.flagPurchase(entryId, true);
                refreshTimeline();
                break;
        }
        return true;
    }

    private void refreshTimeline() {
        EntryList entryList = null;

        // if the user inputs -123456789 and then 123456789
        // or any other invalid date range
        // either a InvalidDateException
        // or a InvalidDateInterval exception will be thrown
        // you should probably catch them both by catching
        // the UserInputException and printing out
        // an error message to the user with UserInputException's
        // getUserErrorMessage method
        switch(visibility) {
            case Income:
                entryList = entryFetcher.fetchAllIncomeEntrys(startDate,endDate);
                break;
            case Expenses:
                entryList = entryFetcher.fetchAllPurchaseEntrys(startDate,endDate);
                break;
            case Both:
                entryList = entryFetcher.fetchAllEntrys(startDate,endDate);
                break;
        }
        this.entries = entryList.getReverseChrono();
        entryAdapter.updateEntries(this.entries);
    }

    private void openNewEntryActivity(){
        Intent i = new Intent(this, NewEntryActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        refreshTimeline();
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == DATE_RANGE_REQUEST) {
            if(data.hasExtra("start_date") && data.hasExtra("end_date")) {
                Bundle extras = data.getExtras();
                hasDateFilter = true;
                invalidateOptionsMenu();
                startDate = extras.getString("start_date");
                endDate = extras.getString("end_date");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        incomeToggle = menu.findItem(R.id.action_toggle_income);
        expensesToggle = menu.findItem(R.id.action_toggle_expenses);
        dateFilterToggle = menu.findItem(R.id.action_filter_by_date);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        incomeToggle.setVisible(true);
        expensesToggle.setVisible(true);

        if(visibility.isIncomeVisible()) {
            incomeToggle.setTitle(getString(R.string.action_hide_income));
            // Don't allow the user to hide both types of entries
            if(!visibility.areExpensesVisible()) {
                incomeToggle.setVisible(false);
            }
        } else {
            incomeToggle.setTitle(getString(R.string.action_show_income));
        }

        if(visibility.areExpensesVisible()) {
            expensesToggle.setTitle(getString(R.string.action_hide_expenses));
            // Don't allow the user to hide both types of entries
            if(!visibility.isIncomeVisible()) {
                expensesToggle.setVisible(false);
            }
        } else {
            expensesToggle.setTitle(getString(R.string.action_show_expenses));
        }

        if(hasDateFilter) {
            dateFilterToggle.setTitle(getString(R.string.action_remove_date_filter));
        } else {
            dateFilterToggle.setTitle(getString(R.string.action_filter_by_date));
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_toggle_income) {
            visibility = visibility.toggleIncome();
        } else if (id == R.id.action_toggle_expenses) {
            visibility = visibility.toggleExpenses();
        }
        if(id == R.id.action_toggle_income || id == R.id.action_toggle_expenses) {
            refreshTimeline();
            // Make sure Android updates the options menu next time it gets displayed
            invalidateOptionsMenu();
            return true;
        }

        if(id == R.id.action_filter_by_date) {
            if(hasDateFilter) {
                hasDateFilter = false;
                startDate = "past";
                endDate = "now";
                refreshTimeline();
                invalidateOptionsMenu();
            } else {
                // hasDateFilter is set to true in onActivityResult
                Intent i = new Intent(this, DateRangeActivity.class);
                startActivityForResult(i, DATE_RANGE_REQUEST);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

