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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/*
This test tests user story #63
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FlagEntryTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void flagEntryTest() {
        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        relativeLayout.perform(longClick());

        ViewInteraction frameLayout = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class), isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction listView = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                        childAtPosition(
                                allOf(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.title), withText("Flag"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Flag")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Flag"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.description), withText("Gas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Gas")));

        ViewInteraction relativeLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        relativeLayout2.perform(longClick());

        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.title), withText("Unflag"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Unflag")));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Unflag"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.description), withText("Gas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Gas")));
        // TODO: check red

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.description), withText("Gas"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler),
                                        0),
                                0),
                        isDisplayed()));
        textView5.check(matches(withText("Gas")));
        // TODO: check black
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
