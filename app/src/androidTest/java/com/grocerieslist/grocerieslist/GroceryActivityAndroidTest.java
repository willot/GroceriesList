package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Checks;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.TestHelpers.RecyclerViewLenghtMatcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;

/**
 * Created by vwillot on 7/27/2017.
 */

public class GroceryActivityAndroidTest {
    private final Context mContext = InstrumentationRegistry.getTargetContext();
    private final Class mDbHelperClass = DataBaseHelper.class;

    @Rule
    public ActivityTestRule<GroceryActivity> mActivityRule = new ActivityTestRule<>(GroceryActivity.class);

    @After
    public void tearDown(){
        deleteTheDatabase();
    }

    public void deleteTheDatabase(){
        try {
            /* Use reflection to get the database name from the db helper class */
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String)f.get(null));
        }catch (NoSuchFieldException ex){
            fail("Make sure you have a member called DATABASE_NAME in the WaitlistDbHelper");
        }catch (Exception ex){
            fail(ex.getMessage());
        }

    }

    @Test
    public void testGorcerieActivityEditTextAndButton() throws Exception {
        ViewInteraction editText = onView(withId(R.id.new_item));
        ViewInteraction button = onView(withId(R.id.new_item_button));

        editText.check(matches(withHint("Add new Item")));
        button.check(matches(withText("+")));
    }

    @Test
    public void testIfDatabaseEmptyYouShouldSeeNewlyCreatedItem() throws Exception {

        ViewInteraction editText = onView(withId(R.id.new_item));
        ViewInteraction button = onView(withId(R.id.new_item_button));
        editText.perform(typeText("BOB1"));
        button.perform(click());

        onView(withId(R.id.my_recycler_view))
                .check(matches(hasDescendant(withText("BOB1"))));

    }

    @Test
    public void testCreateMultipleItemsIncreaseCursorSize() throws Exception {

        ViewInteraction editText = onView(withId(R.id.new_item));
        ViewInteraction button = onView(withId(R.id.new_item_button));

        editText.perform(typeText("BOB1"));
        button.perform(click());
        editText.perform(typeText("BOB2"));
        button.perform(click());
        editText.perform(typeText("BOB3"));
        button.perform(click());
        editText.perform(typeText("BOB4"));
        button.perform(click());
        editText.perform(typeText("BOB5"));
        button.perform(click());

        onView(withId(R.id.my_recycler_view))
                .check(matches(RecyclerViewLenghtMatcher.withLenght(5)));

    }

    @Test
    public void testSwapLeftOrRightRemoveItem() throws InterruptedException {

        ViewInteraction editText = onView(withId(R.id.new_item));
        ViewInteraction button = onView(withId(R.id.new_item_button));

        editText.perform(typeText("BOB1"));
        button.perform(click());
        editText.perform(typeText("BOB2"));
        button.perform(click());
        editText.perform(typeText("BOB3"));
        button.perform(click());
        editText.perform(typeText("BOB4"));
        button.perform(click());

        onView(withId(R.id.my_recycler_view))
                .check(matches(RecyclerViewLenghtMatcher.withLenght(4)));
        onView(withText("BOB1")).check(matches(isDisplayed()));
        onView(withText("BOB1")).perform(swipeRight());
        Thread.sleep(500);

        onView(withId(R.id.my_recycler_view))
                .check(matches(RecyclerViewLenghtMatcher.withLenght(3)));

        onView(withText("BOB1")).check(doesNotExist());


//        onView(withId(R.id.my_recycler_view))
//                .check(matches(hasDescendant(withText("BOB1"))));

    }



}
