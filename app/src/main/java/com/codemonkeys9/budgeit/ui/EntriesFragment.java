package com.codemonkeys9.budgeit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.dso.entry.Entry;
import com.codemonkeys9.budgeit.dso.entrylist.EntryList;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uicategoryfetcher.UICategoryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import java.util.List;

public class EntriesFragment extends Fragment {
    private EntryAdapter entryAdapter;
    private EntryVisibility visibility = EntryVisibility.Both; // defaults to all entries

    // TODO: MAKE THESE PRIVATE
    //       MainActivity relies on them right now
    String startDate = "past";
    String endDate = "now";
    boolean hasDateFilter = false;

    private UIEntryManager entryManager;
    private UIEntryFetcher entryFetcher;
    private List<Entry> entries;

    private UICategoryFetcher categoryFetcher;
    private UICategoryCreator categoryCreator;
    private UIEntryCategorizer entryCategorizer;
    private OnFragmentInteractionListener listener;

    private MenuItem incomeToggle;
    private MenuItem expensesToggle;
    private MenuItem dateFilterToggle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entries, container, false);
        RecyclerView recycler = v.findViewById(R.id.recycler);
        recycler.setAdapter(this.entryAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);

        return v;
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
                Intent i = new Intent(getActivity(), DateRangeActivity.class);
                startActivityForResult(i, MainActivity.DATE_RANGE_REQUEST);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.entryManager = UIEntryManagerFactory.createUIEntryManager();
        this.entryFetcher = UIEntryFetcherFactory.createUIEntryFetcher();
        this.categoryFetcher = UICategoryFetcherFactory.createUICategoryFetcher();
        categoryCreator = UICategoryCreatorFactory.createUICategoryCreator();
        entryCategorizer = UIEntryCategorizerFactory.createUIEntryCategorizer();

        EntryList entryList = entryFetcher.fetchAllEntrys();
        this.entries = entryList.getReverseChrono();

        // Add fake data if there's no data in the DB already
        if(entries.isEmpty()) {
            int games = categoryCreator.createBudgetCategory("80", "Games");
            int misc = categoryCreator.createBudgetCategory("200", "Miscellaneous");
            int income = categoryCreator.createSavingsCategory("2000", "Income");
            int transportation = categoryCreator.createBudgetCategory("100", "Transportation");
            int alyx = entryManager.createEntry("60", "Half-Life: Alyx Pre-order", "2019-12-01",true);
            entryCategorizer.categorizeEntry(alyx, games);
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
            entryList = entryFetcher.fetchAllEntrys();
            entries = entryList.getReverseChrono();
        }

        this.entryAdapter = new EntryAdapter(entries);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        refreshTimeline();
        super.onResume();
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
                entryManager.deleteEntry(entryId);
                refreshTimeline();
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

    public interface OnFragmentInteractionListener {

    }
}
