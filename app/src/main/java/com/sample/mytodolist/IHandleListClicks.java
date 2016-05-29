package com.sample.mytodolist;

public interface IHandleListClicks {
        public void handleClick(ToDoListItem data);

        public void handleMoreOptions(String list_id, String item_id);

        public void handleClickToCreateNewReminder(String data);

        public void handleClickToUpdateReminder(String id, String data);

        public void handleDoneClick(int  position);
    }