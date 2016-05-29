package com.sample.mytodolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sample.mytodolist.ToDoListItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nat on 5/10/16.
 */
public class ToDoListSQLHelper extends SQLiteOpenHelper {

    private static final String TAG = ToDoListSQLHelper.class.getSimpleName();
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "mytodolist.db";
    private static ToDoListSQLHelper mInstance;

    private ToDoListSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    public static ToDoListSQLHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ToDoListSQLHelper(context.getApplicationContext());
        }


        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCrate " + DbSchema.lists_table.createCommand);
        db.execSQL(DbSchema.lists_table.createCommand);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //=====================RELATED TO LIST STUFF================================================
    public void insertIntoListTable(ToDoListItem toDoListItem) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.lists_table.cols.LIST_TITLE, toDoListItem.getText());
        values.put(DbSchema.lists_table.cols.LIST_ITEM_IS_DONE, toDoListItem.isDone() == true ? 1 : 0);
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(DbSchema.lists_table.NAME,
                null,
                values);
    }


    public Cursor getAllListNames() {
        Log.d(TAG, "inside getAllListNames()");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbSchema.lists_table.NAME, //table
                DbSchema.lists_table.ALL_COLUMNS, //columns
                null,//select
                null,//selection args
                null,//group
                null,//having
                null,//order
                null);//limit

        return c;
    }

    public void updateToDoItem(ToDoListItem toDoListItem) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.lists_table.cols.LIST_TITLE, toDoListItem.getText());
        SQLiteDatabase db = this.getWritableDatabase();

        db.update(DbSchema.lists_table.NAME,
                values,
                DbSchema.lists_table.cols.LIST_ID + "= " + toDoListItem.getListId(), null
        );
    }

    public void deleteToDoItem(ToDoListItem toDoListItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(DbSchema.lists_table.NAME,
                    DbSchema.lists_table.cols.LIST_ID + "=" + toDoListItem.getListId(), null);
            db.setTransactionSuccessful();
        } catch (Exception ex) {

        } finally {
            db.endTransaction();
        }
    }
}
