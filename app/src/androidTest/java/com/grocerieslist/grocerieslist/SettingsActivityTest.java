package com.grocerieslist.grocerieslist;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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
//        onData(allOf(is(instanceOf(Preference.class)),
//                withKey("email"),
//                withTitle(R.string.email_address_title)
//                )).onChildView(withText(R.string.email_address_title)).check(matches(isCompletelyDisplayed()));
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

        String expectedEmail = "AHAHAHAHBUSBUS";

        onView(withText("AHAHAHAH")).check(matches(isDisplayed()));
        ViewInteraction viewInteraction = onView(withText("AHAHAHAH"));

        viewInteraction.perform(click()).perform(typeText("BUSBUS"));

        onView(withText("AHAHAHAH")).perform(clickDrawables());

        Thread.sleep(500);

        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String defaultEmailValue = defaultSharedPreferences.getString("email", "null");
        assertEquals(expectedEmail,defaultEmailValue);

    }

    public static ViewAction clickDrawables() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints()//must be a textview with drawables to do perform
            {
                return allOf(isAssignableFrom(TextView.class), new BoundedMatcher<View, TextView>(TextView.class) {
                    @Override
                    protected boolean matchesSafely(final TextView tv) {
                        if (tv.requestFocusFromTouch())//get fpocus so drawables become visible
                            for (Drawable d : tv.getCompoundDrawables())//if the textview has drawables then return a match
                                if (d != null)
                                    return true;

                        return false;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("has drawable");
                    }
                });
            }

            @Override
            public String getDescription() {
                return "click drawables";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                TextView tv = (TextView) view;
                if (tv != null && tv.requestFocusFromTouch())//get focus so drawables are visible
                {
                    Drawable[] drawables = tv.getCompoundDrawables();

                    Rect tvLocation = new Rect();
                    tv.getHitRect(tvLocation);

                    Point[] tvBounds = new Point[4];//find textview bound locations
                    tvBounds[0] = new Point(tvLocation.left, tvLocation.centerY());
                    tvBounds[1] = new Point(tvLocation.centerX(), tvLocation.top);
                    tvBounds[2] = new Point(tvLocation.right, tvLocation.centerY());
                    tvBounds[3] = new Point(tvLocation.centerX(), tvLocation.bottom);

                    for (int location = 0; location < 4; location++)
                        if (drawables[location] != null) {
                            Rect bounds = drawables[location].getBounds();
                            tvBounds[location].offset(bounds.width() / 2, bounds.height() / 2);//get drawable click location for left, top, right, bottom
                            if (tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, tvBounds[location].x, tvBounds[location].y, 0)))
                                tv.dispatchTouchEvent(MotionEvent.obtain(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, tvBounds[location].x, tvBounds[location].y, 0));
                        }
                }
            }
        };
    }

}

