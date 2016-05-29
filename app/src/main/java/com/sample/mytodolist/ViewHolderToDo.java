package com.sample.mytodolist;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by chaitanyaduse on 5/27/2016.
 */
public class ViewHolderToDo extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv_to_do_text;
    IHandleListClicks iHandleListClicks;
    CheckBox chk_to_do_is_done;
    ToDoListItem toDoListItem;
    int itemPositionInList;

    public ViewHolderToDo(View itemView, final IHandleListClicks iHandleListClicks) {
        super(itemView);
        tv_to_do_text = (TextView) itemView.findViewById(R.id.tv_to_do_text);
        tv_to_do_text.setOnClickListener(this);
        chk_to_do_is_done = (CheckBox) itemView.findViewById(R.id.chk_to_do_is_done);
        this.iHandleListClicks = iHandleListClicks;
        chk_to_do_is_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

                    tv_to_do_text.setText(tv_to_do_text.getText(), TextView.BufferType.SPANNABLE);
                    Spannable spannable = (Spannable) tv_to_do_text.getText();
                    spannable.setSpan(STRIKE_THROUGH_SPAN, 0, tv_to_do_text.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    iHandleListClicks.handleDoneClick(itemPositionInList);
                }
            }
        });


    }

    public void bind(ToDoListItem toDoListItem, int positionInList) {
        this.toDoListItem = toDoListItem;
        this.itemPositionInList = positionInList;
        tv_to_do_text.setText(toDoListItem.getText());
        chk_to_do_is_done.setChecked(toDoListItem.isDone());

    }

    @Override
    public void onClick(View v) {
        iHandleListClicks.handleClick(this.toDoListItem);

    }
}
