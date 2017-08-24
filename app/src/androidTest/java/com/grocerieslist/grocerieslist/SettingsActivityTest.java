package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.PreferenceMatchers.withKey;
import static android.support.test.espresso.matcher.PreferenceMatchers.withSummary;
import static android.support.test.espresso.matcher.PreferenceMatchers.withSummaryText;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by vwillot on 8/14/2017.
 */

public class SettingsActivityTest {

    private Context mContext = getInstrumentation().getTargetContext();
    @Rule
    public ActivityTestRule<SettingsActivity> mSettingsActivity = new ActivityTestRule<>(SettingsActivity.class);



    @Before
    public void setup(){
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
    @Test
    public void testDefaultEmailValue(){
        onView(withText("bob@example.com")).check(matches(isDisplayed()));
        onView(withText(R.string.email_address_title)).check(matches(isDisplayed()));

    }

    @Test
    public void testCanChangeEmailAddressAndSaveItNOTFINISHEDSTRUGGLING() throws InterruptedException {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        editor.putString("email", "AHAHAHAH");
        editor.commit();

        Thread.sleep(500);

//        String expectedEmail = "AHAHAHAHBUSBUS";

        onView(withText("AHAHAHAH")).check(matches(isDisplayed()));
        ViewInteraction viewInteraction = onView(withText("AHAHAHAH"));

//        viewInteraction.perform(click()).perform(typeText("BUSBUS"));
//
//
//        Thread.sleep(500);
//
//        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//        String defaultEmailValue = defaultSharedPreferences.getString("email", "null");
//        assertEquals(expectedEmail,defaultEmailValue);

    }

    @Test
    public void testColorListIsPresentWithItsDefaultValue(){
        onView(withText("random")).check(matches(isDisplayed()));
        onView(withText(R.string.list_color_selection_title)).check(matches(isDisplayed()));
    }

    @Test
    public void testSelectedColorIsShowing(){
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = defaultSharedPreferences.edit();

        editor.putString("list_color", "ORANGE");
        editor.commit();

        onView(withText("ORANGE")).check(matches(isDisplayed()));
    }

    @Test
    public void testCanChangeBackgroundColor(){


        onView(withText(R.string.list_color_selection_title)).perform(click());

        SharedPreferences defaultSharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(mContext);
        String defaultEmailValue = defaultSharedPreferences2.getString("list_color", "null");
        assertEquals("ORANGE",defaultEmailValue);

//        onView(withText("Yellow")).inRoot(isPlatformPopup()).perform(click());


//        onView(ViewMatchers.withContentDescription("Random"))
//                .inRoot(RootMatchers.isPlatformPopup())
//                .perform(ViewActions.click());

//        onView(ViewMatchers.withContentDescription("Blue"))
//                .inRoot(RootMatchers.isPlatformPopup())
//                .check(matches(isDisplayed()));
//        onView(withText("Blue")).check(matches(isDisplayed()));
    }


//
//    private void clickListPreference(String _listPreferenceKey, int _listItemPos){
//        final String listPreferenceKey = _listPreferenceKey;
//        final int listItemPos = _listItemPos;
//
//        mSettingsActivity.runOnUiThread(
//                new Runnable() {
//                    public void run() {
//                        // get a handle to the particular ListPreference
//                        ListPreference listPreference= (ListPreference) mSettingsActivity.findPreference(listPreferenceKey);
//
//                        // bring up the dialog box
//                        mSettingsActivity.getPreferenceScreen().onItemClick( null, null, getPreferencePosition(), 0 );
//
//                        // click the requested item
//                        AlertDialog listDialog = (AlertDialog) listPreference.getDialog();
//                        ListView listView = listDialog.getListView();
//                        listView.performItemClick(listView, listItemPos, 0);
//                    }
//
//                    /***
//                     * Finding a ListPreference is difficult when Preference Categories are involved,
//                     * as the category header itself counts as a position in the preferences screen
//                     * list.
//                     *
//                     * This method iterates over the preference items inside preference categories
//                     * to find the ListPreference that is wanted.
//                     *
//                     * @return The position of the ListPreference relative to the entire preferences screen list
//                     */
//                    private int getPreferencePosition(){
//                        int counter = 0;
//                        PreferenceScreen screen = mActivity.getPreferenceScreen();
//
//                        // loop over categories
//                        for (int i = 0; i < screen.getPreferenceCount(); i++){
//                            PreferenceCategory cat = (PreferenceCategory) screen.getPreference(i);
//                            counter++;
//
//                            // loop over category items
//                            for (int j = 0; j < cat.getPreferenceCount(); j++){
//                                if (cat.getPreference(j).getKey().contentEquals(listPreferenceKey)){
//                                    return counter;
//                                }
//                                counter++;
//                            }
//                        }
//                        return 0; // did not match
//                    }
//                }
//        );
//
//        getInstrumentation().waitForIdleSync();
//    }

}

