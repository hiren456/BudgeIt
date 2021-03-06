package com.codemonkeys9.budgeit.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.database.DatabaseHolder;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreator;
import com.codemonkeys9.budgeit.logiclayer.uicategorycreator.UICategoryCreatorFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizer;
import com.codemonkeys9.budgeit.logiclayer.uientrycategorizer.UIEntryCategorizerFactory;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcher;
import com.codemonkeys9.budgeit.logiclayer.uientryfetcher.UIEntryFetcherFactory;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManager;
import com.codemonkeys9.budgeit.logiclayer.uientrymanager.UIEntryManagerFactory;
import com.codemonkeys9.budgeit.ui.testutils.ToastMatcher;

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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.textViewTextColorMatcher;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.withRecyclerView;


/*
This test tests user story #13,#14,#15 and #30
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class IncomeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void prepDB(){
        DatabaseHolder.getDatabase().clean();
        DatabaseHolder.init();
    }
    @After
    public void waitForToastToDisapear(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createValidIncomeTest() {
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("2020-04-04"));
        onView(withId(R.id.editText_details)).perform(typeText("Textbook"));

        // click submit
        onView(withText("Submit")).perform(click());

        // check to make sure that the entry appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).
                check(matches(textViewTextColorMatcher(0xFF00AA00)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).
                check(matches(withText("200.00")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),0)).
                check(matches(withText("Textbook")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),2)).
                check(matches(withText("April 4, 2020")));

        // delete the entry we made
        onView(withId(R.id.entry_recycler)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());
    }

    @Test
    public void createIncomeWithInvalidDateTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("123456789"));
        onView(withId(R.id.editText_details)).perform(typeText("Textbook"));

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidDateException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void createIncomeWithFutureDateTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("2500-04-23"));
        onView(withId(R.id.editText_details)).perform(typeText("Textbook"));

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new FutureDateException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void createIncomeWithEmptyDateTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText(""));
        onView(withId(R.id.editText_details)).perform(typeText("Textbook"));

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidDateException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void createIncomeWithEmptyAmountTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText(""));
        onView(withId(R.id.editText_date)).perform(typeText("2020-02-21"));
        onView(withId(R.id.editText_details)).perform(typeText("Textbook"));

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidAmountException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
