package com.grocerieslist.grocerieslist.Models;

import static android.R.attr.type;

/**
 * Created by vwillot on 6/30/2017.
 */

public class ItemsToSave {
    private String type;
    private int quantity;
    private String itemName;

    public ItemsToSave(int quantity, String itemName, String type){
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

    public String getType() {
        return type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setType(String type) {
        this.type = type;
    }
}
