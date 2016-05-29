package com.sample.mytodolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by nat on 5/1/16.
 */
public class ToDoListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ToDoListRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<ToDoListItem> mToDoListItems;
    private IHandleListClicks mClickInterface;

    public ToDoListRecyclerAdapter(Context context, ArrayList<ToDoListItem> arrayList, IHandleListClicks ilistClicks) {
        mContext = context;
        mToDoListItems = arrayList;
        mClickInterface = ilistClicks;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.row_todo, parent, false);

        return new ViewHolderToDo(view,mClickInterface);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderToDo)holder).bind(mToDoListItems.get(position),position);
    }


    @Override
    public int getItemCount() {
        return mToDoListItems.size();
    }

}

