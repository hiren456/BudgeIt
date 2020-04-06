package com.codemonkeys9.budgeit.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteEntryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public void prepDB(){
        DatabaseHolder.getDatabase().clean();
        DatabaseHolder.init();
    }
    @Before
    public void createEntryToDelete(){
        prepDB();
        //Create the entry to test on
        onView(withId(R.id.newEntryButton)).perform(click());
        onView(withId(R.id.editText_amount)).perform(typeText("50"));
        onView(withId(R.id.editText_date)).perform(typeText("2020-03-10"));
        onView(withId(R.id.editText_details)).perform(typeText("Tax Return"));

        onView(withId(R.id.button_submit)).perform(click());

        // scroll up so that the new entry is at the top
        onView(withId(R.id.entry_recycler)).perform(scrollToPosition(0));
    }

    @Test
    public void deleteEntryTest() {
        // delete the entry that was made
        onView(withId(R.id.entry_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());

        // check that the entry made is no longer at the top
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),0)).
                check(matches(withText("Gas")));
    }
}
