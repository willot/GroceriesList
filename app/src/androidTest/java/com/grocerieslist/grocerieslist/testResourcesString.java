package com.grocerieslist.grocerieslist;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by vwillot on 6/23/2017.
 */

public class testResourcesString {

    @Test
    public void testResourcesString() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals(appContext.getString(R.string.app_name), "Reminder and notes");
        assertEquals(appContext.getString(R.string.groceries_button), "Add Groceries");
    }
}
