package com.sample.mytodolist;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.sample.mytodolist.data.DbSchema;
import com.sample.mytodolist.data.ToDoListSQLHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ToDoListItemManager {

    private static final String TAG = ListManager.class.getSimpleName();
    public static ToDoListItemManager instance;
    private ToDoListSQLHelper mSqlHelper;




    private ToDoListItemManager(Context context){
        mSqlHelper = ToDoListSQLHelper.getInstance(context);
    }

    public static ToDoListItemManager  getInstance(Context context){
        if(instance==null){
            instance = new ToDoListItemManager(context);
        }

        return instance;
    }



}
