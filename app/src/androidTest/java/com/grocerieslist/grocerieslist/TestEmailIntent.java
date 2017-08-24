package com.grocerieslist.grocerieslist;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import android.support.test.espresso.intent.Intents;
/**
 * Created by vwillot on 8/22/2017.
 */

public class TestEmailIntent {

    @Rule
    public IntentsTestRule<GroceryActivity> mRule = new IntentsTestRule<>(GroceryActivity.class);

    @Test
    public void testPassTheRightDataTOEmail(){
        ViewInteraction editText = onView(withId(R.id.new_item));
        ViewInteraction button = onView(withId(R.id.new_item_button));
        ViewInteraction emailButton = onView(withId(R.id.email_button));


        editText.perform(typeText("BOB1"));
        button.perform(click());
        editText.perform(typeText("BOB2"));
        button.perform(click());

        emailButton.perform(click());

        intended((hasAction(Intent.ACTION_CHOOSER)));

//        intended(hasExtra(Intent.EXTRA_TITLE, equalTo("Send mail...")));
//        intended(allOf(hasExtra(Intent.EXTRA_SUBJECT, equalTo("Groceryyy List")
//        )));

    }

}
