package com.codemonkeys9.budgeit.ui;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.database.Database;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.dso.date.DateFactory;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManager;
import com.codemonkeys9.budgeit.logiclayer.idmanager.IDManagerFactory;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.DateSource;
import com.codemonkeys9.budgeit.logiclayer.uirecurringentrymanager.UIRecurringEntryManagerFactory;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.textViewTextColorMatcher;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.withRecyclerView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecurringEntryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @After
    public void waitForAWhile(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createValidRecurringIncomeAndMakeSureItRecursTest() {
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("1000000"));
        onView(withId(R.id.editText_date)).perform(typeText("2020-03-01"));
        onView(withId(R.id.editText_details)).perform(typeText("Paycheck"));
        onView(withId(R.id.switch_recurring)).perform(click());
        onView(withId(R.id.recurring_weeks)).perform(typeText("2"));

        Database mockDb = spy(DatabaseHolder.getDatabase());
        when(mockDb.getDateLastChecked("Recurring Entry")).thenReturn(DateFactory.fromInts(2020, 3, 14));

        DateSource mockDateSource = mock(DateSource.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, 500);
        java.util.Date halfASecondFromNow = calendar.getTime();
        calendar.add(Calendar.YEAR, 5);
        java.util.Date aYearFromNow = calendar.getTime();
        when(mockDateSource.tomorrowAtMidnight()).thenReturn(halfASecondFromNow, aYearFromNow);
        when(mockDateSource.now()).thenReturn(DateFactory.fromInts(2020, 3, 15));

        EntriesFragment entriesFragment = mActivityTestRule.getActivity().pagerAdapter.getEntriesFragment();
        IDManager idManager = IDManagerFactory.createIDManager();
        entriesFragment.recurringEntryManager = UIRecurringEntryManagerFactory.createUIReccuringEntryManager(idManager, mockDb, mockDateSource);

        // click submit
        onView(withText("Submit")).perform(click());

        // This is called in onCreateView(). However, I had to call it again here in order for me to
        // be able to stub out the necessary methods in Database and DateSource
        entriesFragment.scheduleCheckRecurringEntries();

        // check to make sure that the entry appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).
                check(matches(textViewTextColorMatcher(0xFF00AA00)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).
                check(matches(withText("1000000.00")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),0)).
                check(matches(withText("Paycheck")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),2)).
                check(matches(withText("March 1, 2020")));

        // Delete the entry
        onView(withId(R.id.entry_recycler)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());

        // Wait for the timer to run
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check to make sure that the recurrence of the entry appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).
                check(matches(textViewTextColorMatcher(0xFF00AA00)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).
                check(matches(withText("1000000.00")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),0)).
                check(matches(withText("Paycheck")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),2)).
                check(matches(withText("March 15, 2020")));

        // delete the recurrence we made
        onView(withId(R.id.entry_recycler)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());

        // delete the recurring entry we made
        int recurringEntryId = mockDb.getIDCounter("Entry") - 1; // HACK
        mockDb.deleteRecurringEntry(recurringEntryId);
    }
}
