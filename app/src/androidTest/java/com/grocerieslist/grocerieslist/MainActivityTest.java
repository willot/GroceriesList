package com.grocerieslist.grocerieslist;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;

/**
 * Created by vwillot on 6/23/2017.
 */

public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testGorcerieButtonText() throws Exception {
        ViewInteraction viewInteraction = onView(withId(R.id.groceries));
        viewInteraction.check(matches(withText("Add Groceries")));
    }

    @Test
    public void testGorcerieButtonClickChangeText() throws Exception {
        onView(withId(R.id.groceries)).perform(click()).check(matches(withText("You clicked it")));
    }


    @Test
    public void testGoToGroceriesActivityAfterClickingGrocerieButon() throws Exception {

        Intents.init();

        onView(withId(R.id.groceries)).check(matches(withText("Add Groceries")));
            onView(withId(R.id.groceries)).perform(click());

        intended(hasComponent(GroceryActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void testMenuHasOneOption(){
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.options_menu)).check(matches(isDisplayed()));
    }

}
