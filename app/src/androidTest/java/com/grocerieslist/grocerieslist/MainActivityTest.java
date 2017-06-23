package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Created by vwillot on 6/23/2017.
 */

public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGorcerieButtonLabel() throws Exception {
        onView(withId(R.id.groceries)).check(matches(withText("Add Groceries")));
    }
}
