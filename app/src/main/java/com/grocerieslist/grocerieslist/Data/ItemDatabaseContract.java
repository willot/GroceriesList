package com.grocerieslist.grocerieslist.Data;

import android.provider.BaseColumns;

/**
 * Created by vwillot on 7/5/2017.
 */
public class ItemDatabaseContract{

public final static class ItemListContract implements BaseColumns {
    private static final String TABLE_NAME = "item_List";
    private static final String COLUMN_QUANTITY = "item_Quantity";
    private static final String COLUMN_ITEM_NAME = "item_Name";
    private static final String COLUMN_TYPE = "item_Type_ID";

}

public final static class ItemTypeContract implements BaseColumns{
    private static final String TABLE_NAME = "item_Type";
    private static final String COLUMN_TYPE_NAME = "type_Name";
}
}