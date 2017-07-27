package com.grocerieslist.grocerieslist.ModelsTest;

import com.grocerieslist.grocerieslist.Models.ItemsToSave;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vwillot on 6/30/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemToSaveTest {

    @Test
    public void itemToSaveConstructorTest(){
        int expectedQuantity = 5;
        String expectedItemName = "Bus Bus";
        String expectedType = "BB";

        ItemsToSave itemToSave = new ItemsToSave(expectedQuantity, expectedItemName, expectedType);
        assertEquals(expectedQuantity, itemToSave.getQuantity());
        assertEquals(expectedItemName, itemToSave.getItemName());
        assertEquals(expectedType, itemToSave.getType());
    }

    @Test
    public void setQuantityTest(){
        int quantity = 5;
        int expectedQuantity = 15;
        String expectedItemName = "Bus Bus";
        String expectedType = "BB";
        ItemsToSave itemToSave = new ItemsToSave(quantity, expectedItemName, expectedType);

        itemToSave.setQuantity(expectedQuantity);

        assertEquals(expectedQuantity, itemToSave.getQuantity());
    }

    @Test
    public void setItemNameTest(){
        int expectedQuantity = 15;
        String ItemName = "Bus Bus";
        String expectedItemName = "Bob Bob";
        String expectedType = "BB";

        ItemsToSave itemToSave = new ItemsToSave(expectedQuantity, ItemName, expectedType);

        itemToSave.setItemName(expectedItemName);

        assertEquals(expectedItemName, itemToSave.getItemName());
    }

    @Test
    public void setTypeTest(){
        int expectedQuantity = 15;
        String expectedItemName = "Bob Bob";
        String type = "BB";
        String expectedType = "BB";

        ItemsToSave itemToSave = new ItemsToSave(expectedQuantity, expectedItemName, type);

        itemToSave.setType(expectedType);

        assertEquals(expectedType, itemToSave.getType());
    }
}
