package com.sample.mytodolist;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by nat on 5/1/16.
 */
public class ToDoListItem implements IToDoItem,Serializable{
    private String mText;
    private int mList_id;
    private boolean isDone = false;
    private boolean isReminderAlarmSet=false;
    private long mAlarmDttm;
    public ToDoListItem() {

    }

    public ToDoListItem(int item_id, String text, boolean isDone,boolean isAlarm,long alarmwhen) {
        mText = text;
        mList_id = item_id;
        this.isDone = isDone;
        this.isReminderAlarmSet = isAlarm;
        this.mAlarmDttm = alarmwhen;
    }

    public int getListId() {
        return mList_id;
    }

    public void setListId(int list_id) {
        mList_id = list_id;
    }



    @Override
    public String getText() {
        return mText;
    }

    @Override
    public void setText(String s) {
        mText = s;
    }


    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isReminderAlarmSet() {
        return isReminderAlarmSet;
    }

    public void setReminderAlarmSet(boolean reminderAlarmSet) {
        isReminderAlarmSet = reminderAlarmSet;
    }

    public long getmAlarmDttm() {
        return mAlarmDttm;
    }

    public void setmAlarmDttm(long mAlarmDttm) {
        this.mAlarmDttm = mAlarmDttm;
    }
}
