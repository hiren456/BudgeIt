package com.codemonkeys9.budgeit.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.codemonkeys9.budgeit.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


/*
This test tests user story #22 and #19
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class BothTimelineTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void bothTimelineTest() {
        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        relativeLayout.perform(longClick());

        ViewInteraction textView = onView(
                allOf(withId(R.id.description), withText("Gas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Gas")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.date), withText("February 22, 2020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("February 22, 2020")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.amount), withText("50.00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("50.00")));
        // TODO: check green

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.description), withText("Paycheck"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        1),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Paycheck")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.date), withText("February 15, 2020"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        1),
                                2),
                        isDisplayed()));
        textView5.check(matches(withText("February 15, 2020")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.amount), withText("1000.00"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        1),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("1000.00")));
        // TODO: check red
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
