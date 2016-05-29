package com.sample.mytodolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class AddUpdateToDoItemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_todo_description;
    private Button btn_todo_save, btn_todo_delete, btn_add_remove_alarm, btn_select_date, btn_select_time;
    private LinearLayout layout_select_date_time;
    private boolean isUpdateMode = false;
    private TextView tv_alarm_dttm;
    private ToDoListItem toDoListItem;
    private Calendar reminderDateTime;
    private final String dateFormatString = "hh:mm a,dd MMM yyyy";
    private SimpleDateFormat simpleDateFormat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_todo);
        reminderDateTime = Calendar.getInstance();
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


        tv_alarm_dttm.setVisibility(View.GONE);
        layout_select_date_time.setVisibility(View.GONE);

        if (getIntent().getSerializableExtra(MainActivity.TO_DO_ITEM_BUNDLE) != null) {
            toDoListItem = (ToDoListItem) getIntent().getSerializableExtra(MainActivity.TO_DO_ITEM_BUNDLE);
            isUpdateMode = true;
            et_todo_description.setText(toDoListItem.getText());
            btn_todo_delete.setVisibility(View.VISIBLE);
            if (toDoListItem.isReminderAlarmSet()) {
                tv_alarm_dttm.setVisibility(View.VISIBLE);
                btn_add_remove_alarm.setText("Remove");
            }
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
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        ListManager.getInstance(getApplicationContext()).deleteListItem(toDoListItem);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }.execute();

                break;
            case R.id.btn_todo_save:
                toDoListItem.setText(et_todo_description.getText().toString());
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        if (isUpdateMode)
                            ListManager.getInstance(getApplicationContext()).updateListItem(toDoListItem);
                        else
                            ListManager.getInstance(getApplicationContext()).createNewListItem(toDoListItem);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }.execute();

                break;
            case R.id.btn_add_remove_alarm:
                if (toDoListItem.isReminderAlarmSet()) {
                    toDoListItem.setReminderAlarmSet(false);
                    toDoListItem.setmAlarmDttm(0);

                } else {
                    if (btn_add_remove_alarm.getText().toString().contentEquals("Save")) {
                        toDoListItem.setmAlarmDttm(reminderDateTime.getTimeInMillis());
                        toDoListItem.setReminderAlarmSet(true);
                        Toast.makeText(getApplicationContext(), "Reminder alarm set successfully ", Toast.LENGTH_SHORT).show();
                        btn_add_remove_alarm.setText("Remove");
                    } else {
                        layout_select_date_time.setVisibility(View.VISIBLE);
                        tv_alarm_dttm.setVisibility(View.VISIBLE);
                        btn_add_remove_alarm.setText("Save");
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

}
