package com.grocerieslist.grocerieslist.TestHelpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by vwillot on 8/3/2017.
 */

public class RecyclerViewLenghtMatcher {

        public static org.hamcrest.Matcher<View> withLenght(final int size) {
            return new TypeSafeMatcher<View>() {

                @Override
                public void describeTo(Description description) {
                    description.appendText("Lenght of recycler view is not correct");
                }

                @Override
                protected boolean matchesSafely(View view) {
                    return ((RecyclerView) view).getAdapter().getItemCount() == size;
                }
            };
        }

}
