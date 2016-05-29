package com.sample.mytodolist;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.sample.mytodolist.data.DbSchema;
import com.sample.mytodolist.data.ToDoListSQLHelper;

import java.util.ArrayList;

/**
 * Created by nat on 5/10/16.
 */
public class ListManager {

    private static final String TAG = ListManager.class.getSimpleName();
    public static ListManager instance;
    private ToDoListSQLHelper mSqlHelper;
    private ArrayList<ToDoListItem> mListTitles;


    private ListManager(Context context) {
        mSqlHelper = ToDoListSQLHelper.getInstance(context);
        mListTitles = new ArrayList<>();
    }

    public static ListManager getInstance(Context context) {
        if (instance == null) {
            instance = new ListManager(context);
        }

        return instance;
    }

    public ArrayList<ToDoListItem> getListTitles() {
        Cursor c = mSqlHelper.getAllListNames();
        mListTitles.clear();
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            int colIndex = c.getColumnIndex(DbSchema.lists_table.cols.LIST_ID);
            int _id = c.getInt(colIndex);
            colIndex = c.getColumnIndex(DbSchema.lists_table.cols.LIST_TITLE);
            String text = c.getString(colIndex);
            colIndex = c.getColumnIndex(DbSchema.lists_table.cols.LIST_ITEM_ALARM_WHEN);
            long alarm = c.getLong(colIndex);
            colIndex = c.getColumnIndex(DbSchema.lists_table.cols.LIST_ITEM_IS_ALARM);

            boolean isAlarmalarm = c.getInt(colIndex)>0?true:false;
            colIndex = c.getColumnIndex(DbSchema.lists_table.cols.LIST_ITEM_IS_DONE);
            boolean isDone = c.getInt(colIndex)>0?true:false;

            ToDoListItem toDoListItem = new ToDoListItem(_id, text, isDone,isAlarmalarm,alarm);
            mListTitles.add(toDoListItem);
            c.moveToNext();
        }
        return mListTitles;

    }

    public void createNewListItem(ToDoListItem toDoListItem) {
        //check if list name already exists


        mSqlHelper.insertIntoListTable(toDoListItem);

    }

    public void deleteListItem(ToDoListItem toDoListItem) {
        mSqlHelper.deleteToDoItem(toDoListItem);
    }

    public void updateListItem(ToDoListItem toDoListItem) {
        mSqlHelper.updateToDoItem(toDoListItem);
    }


}
