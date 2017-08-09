package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.grocerieslist.grocerieslist.Data.DataBaseHelper;
import com.grocerieslist.grocerieslist.TestHelpers.RecyclerViewLenghtMatcher;
import com.grocerieslist.grocerieslist.TestHelpers.RecyclerViewMatcher;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.grocerieslist.grocerieslist.R.id.my_recycler_view;
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

        onView(withId(my_recycler_view))
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

        onView(withId(my_recycler_view))
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

        onView(withId(my_recycler_view))
                .check(matches(RecyclerViewLenghtMatcher.withLenght(4)));
        onView(withText("BOB1")).check(matches(isDisplayed()));
        onView(withText("BOB1")).perform(swipeRight());
        Thread.sleep(500);

        onView(withId(my_recycler_view))
                .check(matches(RecyclerViewLenghtMatcher.withLenght(3)));

        onView(withText("BOB1")).check(doesNotExist());

    }

    @Test
    public void testCanScrollDownRecyclerView(){

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
        editText.perform(typeText("BOB6"));
        button.perform(click());


        onView(withId(R.id.my_recycler_view)).perform(RecyclerViewActions.scrollToPosition(5));

        onView(withText("BOB5")).check(matches(isDisplayed()));
        onView(withText("BOB6")).check(matches(isDisplayed()));

    }

    @Test
    public void testEmailChooserAppearWhenClickingEmailButton() throws InterruptedException {
        ViewInteraction editText = onView(withId(R.id.new_item));
        ViewInteraction button = onView(withId(R.id.new_item_button));
        ViewInteraction emailButton = onView(withId(R.id.email_button));


        editText.perform(typeText("BOB1"));
        button.perform(click());
        editText.perform(typeText("BOB2"));
        button.perform(click());

        Intents.init();
        emailButton.perform(click());

        intended(hasAction(Intent.ACTION_CHOOSER));



        Intents.release();
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }



//    @Test
//    public void testEmailIntent2RENAMEJUSTPLAYINGRIGHTNOW(){
//        Intent resultData = new Intent();
//        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//
////        intending(toPackage("com.google.android.gm")).respondWith(result);
//
//
//
//
//
//        ViewInteraction editText = onView(withId(R.id.new_item));
//        ViewInteraction button = onView(withId(R.id.new_item_button));
//        ViewInteraction emailButton = onView(withId(R.id.email_button));
//
//
//        editText.perform(typeText("BOB1"));
//        button.perform(click());
//
//        emailButton.perform(click());
//
//        intended(hasAction(Intent.ACTION_CHOOSER));
////        intended(hasAction(Intent.EXTRA_TEXT));
////
////        Intents.init();
////        intending(hasAction(Intent.ACTION_SEND));
////        intending(hasAction(Intent.EXTRA_EMAIL));
////        intending(hasAction(Intent.EXTRA_SUBJECT));
//////        intending(hasAction(Intent.EXTRA_ALARM_COUNT));
////        intended((hasExtra(Intent.EXTRA_SUBJECT, equalTo("Grocery List"))));
////
////        Intents.release();
//
//
//
//
//    }


}
