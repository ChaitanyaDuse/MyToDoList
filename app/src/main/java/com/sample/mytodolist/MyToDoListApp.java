package com.sample.mytodolist;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by chaitanyaduse on 5/28/2016.
 */
public class MyToDoListApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
