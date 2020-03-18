package com.codemonkeys9.budgeit.ui;


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.textViewTextColorMatcher;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.withRecyclerView;
/*
This test test user story #20
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class PurchaseTimelineTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void purchaseTimelineTest() {
        onView(childAtPosition(withId(R.id.toolbar),2)).perform(click());
        onView(withText("Hide income")).perform(click());

        // Check that the top three entries are red(expenses).
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(0),1)).check(matches(textViewTextColorMatcher(0xFFFF0000)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(1),1)).check(matches(textViewTextColorMatcher(0xFFFF0000)));
        onView(childAtPosition(withRecyclerView(R.id.entry_recycler).atPosition(2),1)).check(matches(textViewTextColorMatcher(0xFFFF0000)));
    }
}
