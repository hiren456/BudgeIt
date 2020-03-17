package com.codemonkeys9.budgeit.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;

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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.BudgitUITestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteEntryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void createEntrytoDelete(){

        //Create the entry to test on
        onView(withId(R.id.newEntryButton)).perform(click());
        onView(withId(R.id.editText_amount)).perform(typeText("50"));
        onView(withId(R.id.editText_date)).perform(typeText("2020-03-10"));
        onView(withId(R.id.editText_details)).perform(typeText("Tax Return"));

        onView(withId(R.id.button_submit)).perform(click());

        // scroll up so that the new entry is at the top
        onView( allOf(isDisplayed(), withId(R.id.recycler))).perform(scrollToPosition(0));
    }

    @Test
    public void deleteEntryTest() {
        // delete the entry that was made
        onView(allOf(isDisplayed(),withId(R.id.recycler))).perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());

        // check that the entry made is no longer at the top
        onView(childAtPosition(withRecyclerView(R.id.recycler).atPosition(0),0)).
                check(matches(withText("Gas")));
    }
}
