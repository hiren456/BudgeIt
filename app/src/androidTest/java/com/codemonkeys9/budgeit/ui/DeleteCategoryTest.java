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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.childAtPosition;
import static com.codemonkeys9.budgeit.ui.testutils.BudgitUITestUtils.withRecyclerView;

/*
This test tests user story #64,67
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteCategoryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void createCatToDelete(){
        // move to category tab
        onView(withText("Categories")).perform(click());

        onView(withText("New category")).perform(click());
        onView(withId(R.id.editText_amount)).perform(typeText("6000"));
        onView(withId(R.id.editText_details)).perform(typeText("Car"),closeSoftKeyboard());

        // click submit
        onView(withText("Submit")).perform(click());

        // scroll up so that the new entry is at the top
        onView(withId(R.id.category_recycler)).perform(scrollToPosition(0));
    }

    @Test
    public void deleteCategoryTest() {
        // delete the entry that was made
        onView(withId(R.id.category_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,longClick()));
        onView(withText("Delete")).perform(click());

        // check that the entry made is no longer at the top
        onView(childAtPosition(withRecyclerView(R.id.category_recycler).atPosition(0),0)).
                check(matches(withText("Transportation")));
    }
}
