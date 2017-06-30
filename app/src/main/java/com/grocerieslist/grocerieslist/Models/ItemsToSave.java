package com.grocerieslist.grocerieslist.Models;

import static android.R.attr.type;

/**
 * Created by vwillot on 6/30/2017.
 */

public class ItemsToSave {
    private int type;
    private int quantity;
    private String itemName;

    public ItemsToSave(int quantity, String itemName, int type){
        this.quantity = quantity;
        this.itemName = itemName;
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getType() {
        return type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setType(int type) {
        this.type = type;
    }
}
