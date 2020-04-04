package com.codemonkeys9.budgeit.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;
import com.codemonkeys9.budgeit.exceptions.InvalidAmountException;
import com.codemonkeys9.budgeit.exceptions.UserInputException;
import com.codemonkeys9.budgeit.ui.testutils.ToastMatcher;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
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
This test tests user story #66,68,69
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SavingsCategoryTest {

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
    public void createValidSavingsCategoryTest() {
        // move to category tab
        onView(withText("Categories")).perform(click());

        // move to new category screen
        onView(withText("New category")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("6000"));
        onView(withId(R.id.editText_details)).perform(typeText("Car"),closeSoftKeyboard());

        // click submit
        onView(withText("Submit")).perform(click());

        // check to make sure that the category appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),2)).
                check(matches(withText("6000.00")));
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),2)).
                check(matches(textViewTextColorMatcher(0xFF00AA00)));
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),0)).
                check(matches(withText("Car")));

        // delete the category we made
        onView(withId(R.id.category_recycler)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());
    }

    @Test
    public void createSavingsWithEmptyAmountTest(){
        // move to category tab
        onView(withText("Categories")).perform(click());

        // move to new category screen
        onView(withText("New category")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText(""));
        onView(withId(R.id.editText_details)).perform(typeText("Car"),closeSoftKeyboard());

        // click submit
        onView(withText("Submit")).perform(click());

        // Test that the proper toast message is displayed
        UserInputException e = new InvalidAmountException("");
        onView(withText("Invalid category: "+e.getUserErrorMessage())).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
