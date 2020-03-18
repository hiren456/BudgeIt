package com.codemonkeys9.budgeit.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.textViewTextColorMatcher;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.withRecyclerView;


/*
This test tests user story #66,68,69
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateValidBudgetCategoryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createValidBudgetCategoryTest() {
        // move to category tab
        onView(withText("Categories")).perform(click());

        // move to new category screen
        onView(withText("New category")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("300"));
        onView(withId(R.id.editText_details)).perform(typeText("Food"),closeSoftKeyboard());

        // select savings
        onView(childAtPosition(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.control_incomeOrExpense),
                                0),
                        0),
                1)).perform(click());

        // click submit
        onView(withText("Submit")).perform(click());

        // check to make sure that the category appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),1)).
                check(matches(withText("300.00")));
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),1)).
                check(matches(textViewTextColorMatcher(0xFFFF0000)));
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),0)).
                check(matches(withText("Food")));

        // delete the category we made
        onView(withId(R.id.category_recycler)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());
    }
}
