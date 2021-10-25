package com.main.mymovies.model;

import android.app.Application;

public class App extends Application {
    /**
     *class that run first to init single ton
     */
    @Override
    public void onCreate() {
        super.onCreate();
        QueueSingleton.init(this);
    }
}
