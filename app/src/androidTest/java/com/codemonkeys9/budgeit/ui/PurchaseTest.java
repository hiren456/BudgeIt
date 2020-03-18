package com.codemonkeys9.budgeit.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.exceptions.FutureDateException;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.InvalidDateException;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.ui.testutils.ToastMatcher;

import org.junit.After;
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
import static org.hamcrest.Matchers.allOf;


/*
This test tests user story #16,#17,#18,#30 and #62
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class PurchaseTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @After
    public void waitForToastToDisapear(){
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createValidEntryTest() {
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("2020-02-21"));
        onView(withId(R.id.editText_details)).perform(typeText("Taxi"));

        // select expense
        onView(childAtPosition(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.control_incomeOrExpense),
                                0),
                        0),
                1)).perform(click());

        // switch bad
        onView(withId(R.id.switch_bad)).perform(click());

        // click submit
        onView(withText("Submit")).perform(click());


        // check to make sure that the entry appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(1),1)).
                check(matches(textViewTextColorMatcher(0xFFFF0000)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(1),1)).
                check(matches(withText("200.00")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(1),0)).
                check(matches(textViewTextColorMatcher(0xFFFF0000)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(1),0)).
                check(matches(withText("Taxi")));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(1),2)).
                check(matches(withText("February 21, 2020")));

        // delete the entry we made
        onView(withId(R.id.entry_recycler)).
                perform(RecyclerViewActions.actionOnItemAtPosition(1,longClick()));
        onView(withText("Delete")).perform(click());
    }

    @Test
    public void createPurchaseWithInvalidDateTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("123456789"));
        onView(withId(R.id.editText_details)).perform(typeText("Taxi"));

        // select expense
        onView(childAtPosition(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.control_incomeOrExpense),
                                0),
                        0),
                1)).perform(click());

        // switch bad
        onView(withId(R.id.switch_bad)).perform(click());

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidDateException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void createPurchaseWithFutureDateTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("2500-04-23"));
        onView(withId(R.id.editText_details)).perform(typeText("Taxi"));

        // select expense
        onView(childAtPosition(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.control_incomeOrExpense),
                                0),
                        0),
                1)).perform(click());

        // switch bad
        onView(withId(R.id.switch_bad)).perform(click());

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new FutureDateException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void createPurchaseWithEmptyDateTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText(""));
        onView(withId(R.id.editText_details)).perform(typeText("Taxi"));

        // select expense
        onView(childAtPosition(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.control_incomeOrExpense),
                                0),
                        0),
                1)).perform(click());

        // switch bad
        onView(withId(R.id.switch_bad)).perform(click());

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidDateException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void createPurchaseWithEmptyAmountTest(){
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText(""));
        onView(withId(R.id.editText_date)).perform(typeText("123456789"));
        onView(withId(R.id.editText_details)).perform(typeText("Taxi"));

        // select expense
        onView(childAtPosition(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.control_incomeOrExpense),
                                0),
                        0),
                1)).perform(click());

        // switch bad
        onView(withId(R.id.switch_bad)).perform(click());

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidAmountException("");
        onView(withText("Invalid entry: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
