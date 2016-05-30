package com.sample.mytodolist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView rv_todos;
    private final ArrayList<ToDoListItem> list = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private ToDoListRecyclerAdapter toDoListRecyclerAdapter;
    private FloatingActionButton fab;
    private final int INTENT_RESPONSE_CODE_ADD = 101;
    private final int INTENT_RESPONSE_CODE_UPDATE = 102;
    public static String TO_DO_ITEM_BUNDLE = "todo_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        rv_todos = (RecyclerView) findViewById(R.id.rv_todos);
        linearLayoutManager = new LinearLayoutManager(this);
        rv_todos.setLayoutManager(linearLayoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddUpdateToDoItemActivity.class);
                startActivityForResult(i, INTENT_RESPONSE_CODE_ADD);
            }
        });
        toDoListRecyclerAdapter = new ToDoListRecyclerAdapter(this, list, new IHandleListClicks() {
            @Override
            public void handleClick(ToDoListItem data) {

                Intent i = new Intent(MainActivity.this, AddUpdateToDoItemActivity.class);
                i.putExtra(TO_DO_ITEM_BUNDLE, data);
                startActivityForResult(i, INTENT_RESPONSE_CODE_UPDATE);
            }

            @Override
            public void handleMoreOptions(String list_id, String item_id) {

            }

            @Override
            public void handleClickToCreateNewReminder(String data) {

            }

            @Override
            public void handleClickToUpdateReminder(String id, String data) {

            }

            @Override
            public void handleDoneClick(final int position) {

                new AsyncTask<ToDoListItem, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        list.remove(position);
                        toDoListRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected Void doInBackground(ToDoListItem... params) {
                        ListManager.getInstance(getApplicationContext()).deleteListItem(params[0]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                    }
                }.execute(list.get(position));


            }
        });

        rv_todos.setAdapter(toDoListRecyclerAdapter);

        populateList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateList();

    }

    private void populateList() {
        new AsyncTask<Void, Void, List<ToDoListItem>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress();
            }

            @Override
            protected List<ToDoListItem> doInBackground(Void... params) {
                return ListManager.getInstance(getApplicationContext()).getListTitles();
            }

            @Override
            protected void onPostExecute(List<ToDoListItem> toDoListItems) {
                super.onPostExecute(toDoListItems);

                if (toDoListItems != null && toDoListItems.size() > 0) {
                    list.clear();
                    list.addAll(toDoListItems);
                    toDoListRecyclerAdapter.notifyDataSetChanged();
                }
                hideProgress();

            }
        }.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        unsetAlarm();

    }

    public void unsetAlarm() {

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.stop();
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent notifyIntent = PendingIntent.getService(MainActivity.this, 0,
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT);  // recreate it here before calling cancel

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.cancel(notifyIntent);




    }
}
