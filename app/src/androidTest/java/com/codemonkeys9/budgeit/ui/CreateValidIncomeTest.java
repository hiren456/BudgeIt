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
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.textViewTextColorMatcher;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;


/*
This test tests user story #13,#14,#15 and #30
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateValidIncomeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createValidIncomeTest() {
        // move to new entry screen
        onView(withText("New entry")).perform(click());

        // fill fields
        onView(withId(R.id.editText_amount)).perform(typeText("200"));
        onView(withId(R.id.editText_date)).perform(typeText("2020-02-21"));
        onView(withId(R.id.editText_details)).perform(typeText("Textbook"));

        // click submit
        onView(withText("Submit")).perform(click());

        // check to make sure that the entry appears on the screen with
        // the proper fields
        onView(childAtPosition(withRecyclerView(R.id.recycler).atPosition(1),1)).
                check(matches(textViewTextColorMatcher(0xFF00AA00)));
        onView(childAtPosition(withRecyclerView(R.id.recycler).atPosition(1),1)).
                check(matches(withText("200.00")));
        onView(childAtPosition(withRecyclerView(R.id.recycler).atPosition(1),0)).
                check(matches(withText("Textbook")));
        onView(childAtPosition(withRecyclerView(R.id.recycler).atPosition(1),2)).
                check(matches(withText("February 21, 2020")));

        // delete the entry we made
        onView(allOf(isDisplayed(),withId(R.id.recycler))).
                perform(RecyclerViewActions.actionOnItemAtPosition(1,longClick()));
        onView(withText("Delete")).perform(click());
    }
}
