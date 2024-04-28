package com.example.task_master;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    public static ArrayList<Task> tasks;

    @Override
    public void onCreate() {
        super.onCreate();

        tasks=new ArrayList<>();

    }
}
