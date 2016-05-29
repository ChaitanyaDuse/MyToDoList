package com.sample.mytodolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chaitanyaduse on 5/28/2016.
 */
public class AddUpdateToDoItemActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_todo_description;
    private Button btn_todo_save, btn_todo_delete, btn_add_remove_alarm, btn_select_date, btn_select_time;
    private LinearLayout layout_select_date_time;
    private boolean isUpdateMode = false;
    private TextView tv_alarm_dttm;
    private ToDoListItem toDoListItem;
    private Calendar reminderDateTime;
    private final String dateFormatString = "hh:mm a,dd MMM yyyy";
    private SimpleDateFormat simpleDateFormat;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private final int ADD = 0;
    private final int UPDATE = 1;
    private final int DELETE = 2;
    private final int REMOVE_ALARM = 3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_todo);
        reminderDateTime = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        simpleDateFormat = new SimpleDateFormat(dateFormatString);
        et_todo_description = (EditText) findViewById(R.id.et_todo_description);
        btn_todo_save = (Button) findViewById(R.id.btn_todo_save);
        btn_todo_delete = (Button) findViewById(R.id.btn_todo_delete);
        btn_add_remove_alarm = (Button) findViewById(R.id.btn_add_remove_alarm);
        tv_alarm_dttm = (TextView) findViewById(R.id.tv_alarm_dttm);
        layout_select_date_time = (LinearLayout) findViewById(R.id.layout_select_date_time);
        btn_select_time = (Button) layout_select_date_time.findViewById(R.id.btn_select_time);
        btn_select_date = (Button) layout_select_date_time.findViewById(R.id.btn_select_date);

        btn_select_time.setOnClickListener(this);
        btn_select_date.setOnClickListener(this);
        btn_todo_save.setOnClickListener(this);
        btn_todo_delete.setOnClickListener(this);
        btn_add_remove_alarm.setOnClickListener(this);

        getApplicationContext().getString(R.string.btn_label_save_alarm);

        if (getIntent().getSerializableExtra(MainActivity.TO_DO_ITEM_BUNDLE) != null) {
            toDoListItem = (ToDoListItem) getIntent().getSerializableExtra(MainActivity.TO_DO_ITEM_BUNDLE);
            isUpdateMode = true;
            et_todo_description.setText(toDoListItem.getText());
            btn_todo_delete.setVisibility(View.VISIBLE);
            initReminderAlarmViews();
        } else {
            isUpdateMode = false;
            toDoListItem = new ToDoListItem();
            btn_todo_delete.setVisibility(View.GONE);

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_todo_delete:
                createUpdateToDoAsync(toDoListItem, DELETE, true);
                break;
            case R.id.btn_todo_save:
                if(et_todo_description.getText().toString().length()>0) {
                    toDoListItem.setText(et_todo_description.getText().toString());
                    if (isUpdateMode)
                        createUpdateToDoAsync(toDoListItem, UPDATE, true);
                    else
                        createUpdateToDoAsync(toDoListItem, ADD, true);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter to do description",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_add_remove_alarm:
                if (toDoListItem.isReminderAlarmSet()) {
                    toDoListItem.setReminderAlarmSet(false);
                    toDoListItem.setmAlarmDttm(0);
                    alarmManager.cancel(getAlarmPendingIntent(toDoListItem));
                    createUpdateToDoAsync(toDoListItem, REMOVE_ALARM, false);


                } else {
                    if (btn_add_remove_alarm.getText().toString().contentEquals(getApplicationContext().getString(R.string.btn_label_save_alarm))) {
                        if (tv_alarm_dttm.getText().toString().length() > 0) {
                            toDoListItem.setmAlarmDttm(reminderDateTime.getTimeInMillis());
                            toDoListItem.setReminderAlarmSet(true);
                            alarmManager.set(AlarmManager.RTC, reminderDateTime.getTimeInMillis(), getAlarmPendingIntent(toDoListItem));
                            createUpdateToDoAsync(toDoListItem, UPDATE, false);
                        } else {
                            Toast.makeText(getApplicationContext(), "Select both date and time", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        layout_select_date_time.setVisibility(View.VISIBLE);
                        tv_alarm_dttm.setVisibility(View.VISIBLE);
                        btn_add_remove_alarm.setText(getApplicationContext().getString(R.string.btn_label_save_alarm));
                    }
                }
                break;
            case R.id.btn_select_date:

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                reminderDateTime.set(Calendar.YEAR, year);
                                reminderDateTime.set(Calendar.MONTH, mMonth);
                                reminderDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                tv_alarm_dttm.setText(simpleDateFormat.format(reminderDateTime.getTimeInMillis()));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_select_time:
                // Get Current Time
                final Calendar c1 = Calendar.getInstance();
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                reminderDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                reminderDateTime.set(Calendar.MINUTE, minute);
                                tv_alarm_dttm.setText(simpleDateFormat.format(reminderDateTime.getTimeInMillis()));
                            }
                        }, c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE), false);
                timePickerDialog.show();
        }


    }

    private void createUpdateToDoAsync(final ToDoListItem toDoListItem, final int action, final boolean isFinish) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress();
            }

            @Override
            protected Void doInBackground(Void... params) {
                if (action == UPDATE || action == REMOVE_ALARM)
                    ListManager.getInstance(getApplicationContext()).updateListItem(toDoListItem);
                else if (action == ADD)
                    ListManager.getInstance(getApplicationContext()).createNewListItem(toDoListItem);
                else if (action == DELETE)
                    ListManager.getInstance(getApplicationContext()).deleteListItem(toDoListItem);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                hideProgress();
                if (isFinish) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else if (action == REMOVE_ALARM) {
                    initReminderAlarmViews();
                    Toast.makeText(getApplicationContext(), "Alarm removed succrssfully", Toast.LENGTH_SHORT).show();
                }

            }
        }.execute();

    }

    private void initReminderAlarmViews() {
        if (toDoListItem.isReminderAlarmSet()) {
            layout_select_date_time.setVisibility(View.VISIBLE);
            tv_alarm_dttm.setVisibility(View.VISIBLE);
            tv_alarm_dttm.setText(simpleDateFormat.format(toDoListItem.getmAlarmDttm()));

            btn_add_remove_alarm.setText(getApplicationContext().getString(R.string.btn_label_remove_alarm));
        } else {
            layout_select_date_time.setVisibility(View.GONE);
            tv_alarm_dttm.setVisibility(View.GONE);
            tv_alarm_dttm.setText("");
            btn_add_remove_alarm.setText(getApplicationContext().getString(R.string.btn_label_add_alarm));
        }
    }

    private PendingIntent getAlarmPendingIntent(ToDoListItem toDoListItem) {
        Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putString("notification", toDoListItem.getText());
        myIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), toDoListItem.getListId(),
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
