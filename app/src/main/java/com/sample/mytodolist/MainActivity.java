package com.sample.mytodolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        list.addAll(ListManager.getInstance(getApplicationContext()).getListTitles());
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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        list.clear();
        list.addAll(ListManager.getInstance(getApplicationContext()).getListTitles());
        toDoListRecyclerAdapter.notifyDataSetChanged();

    }
}
