package com.grocerieslist.grocerieslist;

import android.provider.BaseColumns;

import com.grocerieslist.grocerieslist.Data.ItemDatabaseContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vwillot on 7/5/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class ItemDatabaseContractTest {

    private Class<?>[] innerClasses;

    @Before
    public void setup(){
        innerClasses = ItemDatabaseContract.class.getDeclaredClasses();
    }

    @Test
    public void testNumberOfInnerClass(){
        Class[] innerClasses = ItemDatabaseContract.class.getDeclaredClasses();
        assertEquals(2, innerClasses.length);
        assertEquals("ItemListContract", innerClasses[1].getSimpleName());
        assertEquals("ItemTypeContract", innerClasses[0].getSimpleName());
    }

    @Test
    public void ItemListContractTest() {

        Class<?> itemListContract = innerClasses[1];
        assertTrue("Inner class should implement the BaseColumns interface", BaseColumns.class.isAssignableFrom(itemListContract));
        assertTrue("Inner class should be final", Modifier.isFinal(itemListContract.getModifiers()));
        assertTrue("Inner class should be static", Modifier.isStatic(itemListContract.getModifiers()));

        Field[] allFields = itemListContract.getDeclaredFields();
        assertEquals("There should be exactly 4 String members in the inner class", 4, allFields.length);
        for (Field field : allFields) {
            assertTrue("All members in the contract class should be Strings", field.getType() == String.class);
            assertTrue("All members in the contract class should be final", Modifier.isFinal(field.getModifiers()));
            assertTrue("All members in the contract class should be static", Modifier.isStatic(field.getModifiers()));

        }
    }

    @Test
    public void ItemTypeContractTest() {

        Class<?> itemTypeContract = innerClasses[0];
        assertTrue("Inner class should implement the BaseColumns interface", BaseColumns.class.isAssignableFrom(itemTypeContract));
        assertTrue("Inner class should be final", Modifier.isFinal(itemTypeContract.getModifiers()));
        assertTrue("Inner class should be static", Modifier.isStatic(itemTypeContract.getModifiers()));

        Field[] allFields = itemTypeContract.getDeclaredFields();
        assertEquals("There should be exactly 4 String members in the inner class", 2, allFields.length);
        for (Field field : allFields) {
            assertTrue("All members in the contract class should be Strings", field.getType() == String.class);
            assertTrue("All members in the contract class should be final", Modifier.isFinal(field.getModifiers()));
            assertTrue("All members in the contract class should be static", Modifier.isStatic(field.getModifiers()));

        }
    }
}
