package com.codemonkeys9.budgeit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.NewRecurringEntriesDelegate;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManagerFactory;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EntriesFragment extends Fragment implements EntryAdapter.OnEntryListener{
    private EntryAdapter entryAdapter;
    private EntryVisibility visibility = EntryVisibility.Both; // defaults to all entries

    // Request codes for activities that need to return data
    static final int DATE_RANGE_REQUEST = 0;
    static final int NEW_ENTRY = 1;

    String startDate = "past";
    String endDate = "now";
    boolean hasDateFilter = false;

    UIEntryManager entryManager;
    UIEntryFetcher entryFetcher;
    UIRecurringEntryManager recurringEntryManager;
    EntryList entries;

    private MenuItem incomeToggle;
    private MenuItem expensesToggle;
    private MenuItem dateFilterToggle;

    RecyclerView recycler;

    // This variable is updated when a new entry is created to its ID. This is used so we can scroll
    // to its position. This is necessary because we use ListAdapter and DiffUtil for diffing lists,
    // and that is done asynchronously on a background thread. So we have to register an observer
    // and perform the scrolling there.
    Integer newID = null;
    final RecyclerView.AdapterDataObserver recyclerViewObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if(newID != null) {
                scrollToID(newID);
                newID = null;
            }
        }
    };

    private boolean active = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entries, container, false);
        RecyclerView recycler = v.findViewById(R.id.entry_recycler);
        recycler.setAdapter(this.entryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);

        this.entryAdapter.registerAdapterDataObserver(this.recyclerViewObserver);

        Button newEntryButton = v.findViewById(R.id.newEntryButton);
        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewEntryActivity();
            }
        });

        scheduleCheckRecurringEntries();

        return v;
    }

    void scheduleCheckRecurringEntries() {
        recurringEntryManager.scheduleCheckAllRecurringEntriesEveryDay(
                new NewRecurringEntriesDelegate() {
                    @Override
                    public void receivedNewEntries() {
                        getActivity().runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshTimeline();
                                    }
                                }
                        );
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        this.entryAdapter.unregisterAdapterDataObserver(this.recyclerViewObserver);
    }

    private void openNewEntryActivity() {
        Intent i = new Intent(getContext(), NewEntryActivity.class);
        startActivityForResult(i, NEW_ENTRY);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        incomeToggle = menu.findItem(R.id.action_toggle_income);
        expensesToggle = menu.findItem(R.id.action_toggle_expenses);
        dateFilterToggle = menu.findItem(R.id.action_filter_by_date);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
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

        super.onPrepareOptionsMenu(menu);
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
            getActivity().invalidateOptionsMenu();
            return true;
        }

        if(id == R.id.action_filter_by_date) {
            if(hasDateFilter) {
                hasDateFilter = false;
                startDate = "past";
                endDate = "now";
                refreshTimeline();
                getActivity().invalidateOptionsMenu();
            } else {
                // hasDateFilter is set to true in onActivityResult
                Intent i = new Intent(getContext(), DateRangeActivity.class);
                startActivityForResult(i, DATE_RANGE_REQUEST);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.entryManager = UIEntryManagerFactory.createUIEntryManager();
        this.recurringEntryManager = UIRecurringEntryManagerFactory.createUIReccuringEntryManager();
        this.entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        this.entries = entryFetcher.fetchAllEntrys();
        List<Entry> entryList = entries.getReverseChrono();

        if(entryList.isEmpty()) {
            UICategoryCreator categoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
            UIEntryCategorizer entryCategorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();

            int games = categoryCreator.createBudgetCategory("80", "Games");
            int misc = categoryCreator.createBudgetCategory("200", "Miscellaneous");
            int income = categoryCreator.createSavingsCategory("2000", "Income");
            int transportation = categoryCreator.createBudgetCategory("100", "Transportation");
            int alyx = entryManager.createEntry("60", "Half-Life: Alyx Pre-order", "2019-12-01", true);
            System.out.println("Games has id: "+games);
            System.out.println("Misc has id: "+misc);
            System.out.println("Income has id: "+income);
            System.out.println("Transportation has id: "+transportation);
            entryCategorizer.categorizeEntry(alyx, games);
            ADD_FAKE_DATA:
            for (int year = 2018; year <= 2020; year++) {
                for (int month = 1; month <= 12; month++) {
                    if (year == 2020 && month > 3) break;
                    if (year == 2020 && month > 2) break ADD_FAKE_DATA;
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

                        int entry = entryManager.createEntry("50", "Gas", year + "-" + monthString + "-" + dayString, true);
                        entryCategorizer.categorizeEntry(entry, transportation);
                    }
                    // Paycheck every two weeks-ish
                    int pay1 = entryManager.createEntry("1000", "Paycheck", year + "-" + monthString + "-01", false);
                    int pay2 = entryManager.createEntry("1000", "Paycheck", year + "-" + monthString + "-15", false);
                    entryCategorizer.categorizeEntry(pay1, income);
                    entryCategorizer.categorizeEntry(pay2, income);

                    int what = entryManager.createEntry(
                            "120",
                            "Something with an extremely, exceptionally, extraordinarily, staggeringly, shockingly, positively supercalifragilisticexpialidociously long description",
                            year + "-" + monthString + "-13",
                            true
                    );
                    entryCategorizer.categorizeEntry(what, misc);
                }
            }
            entries = entryFetcher.fetchAllEntrys();
        }

        this.entryAdapter = new EntryAdapter(entries.getReverseChrono());
    }

    @Override
    public void onResume() {
        this.active = true;
        this.recycler = getView().findViewById(R.id.entry_recycler);
        refreshTimeline();
        super.onResume();
    }

    @Override
    public void onPause() {
        this.active = false;
        super.onPause();
    }


    private void refreshTimeline() {
        switch(visibility) {
            case Income:
                this.entries = entryFetcher.fetchAllIncomeEntrys(startDate,endDate);
                break;
            case Expenses:
                this.entries = entryFetcher.fetchAllPurchaseEntrys(startDate,endDate);
                break;
            case Both:
                this.entries = entryFetcher.fetchAllEntrys(startDate,endDate);
                break;
        }
        entryAdapter.updateEntries(this.entries.getReverseChrono());
    }

    public void scrollToID(int entryID) {
        int target = this.entries.getReverseChronoIndexOfEntryWithID(entryID);
        recycler.smoothScrollToPosition(target);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == DATE_RANGE_REQUEST) {
                if (data.hasExtra("start_date") && data.hasExtra("end_date")) {
                    Bundle extras = data.getExtras();
                    hasDateFilter = true;
                    startDate = extras.getString("start_date");
                    endDate = extras.getString("end_date");
                }
            } else if(requestCode == NEW_ENTRY) {
                if(data.hasExtra("newly_created_entry_id")) {
                    refreshTimeline();
                    Bundle extras = data.getExtras();
                    this.newID = extras.getInt("newly_created_entry_id");
                }
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // We will get context item events for all fragments in MainPager. We have to return false
        // in order for other fragments to have a chance to handle them.
        if(!this.active) return false;

        // Get index *within the currently-displayed list of entries*
        int entryIndex = item.getGroupId();
        // Get actual, global entry ID
        int entryId = entries.getReverseChrono().get(entryIndex).getEntryID();
        int buttonId = item.getItemId();

        entryAdapter.onContextItemSelected(getContext(), entryId, buttonId);
        refreshTimeline();
        return true;
    }


    @Override
    public void onEntryClick(int position) {
        //do nothing
    }
}
