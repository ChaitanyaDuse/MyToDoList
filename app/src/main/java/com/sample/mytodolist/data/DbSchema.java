package com.sample.mytodolist.data;

/**
 * Created by nat on 5/10/16.
 */
public class DbSchema {

    //=========================================================================
    public static final class lists_table {
        public static final String NAME = "listOfLists";
        public static final String[] ALL_COLUMNS = new String[]{cols.LIST_ID, cols.LIST_TITLE,
                cols.LIST_ITEM_IS_DONE,cols.LIST_ITEM_ALARM_WHEN,cols.LIST_ITEM_IS_ALARM};
        public static final String createCommand =
                "CREATE TABLE " + NAME + "(" +
                        cols.LIST_ID + " INTEGER PRIMARY KEY, " +
                        cols.LIST_ITEM_IS_DONE + " INTEGER, " +
                        cols.LIST_ITEM_IS_ALARM + " INTEGER, " +
                        cols.LIST_ITEM_ALARM_WHEN + " INTEGER, " +
                        cols.LIST_TITLE + " TEXT " +
                        ")";

        public static final class cols {
            public static final String LIST_ID = "list_id";
            public static final String LIST_TITLE = "list_title";
            public static final String LIST_ITEM_IS_DONE = "list_is_done";
            public static final String LIST_ITEM_IS_ALARM = "list_is_alarm_set";
            public static final String LIST_ITEM_ALARM_WHEN = "list_alarm_when";
        }
    }


}
