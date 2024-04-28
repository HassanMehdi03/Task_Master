package com.example.task_master;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;

public class EditTasksMain extends AppCompatActivity implements ListFrag.NameClicked {

    FragmentManager manager;
    Fragment fList;

    Task tasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tasks_main);

        init();

        manager.beginTransaction().
                show(fList).
                commit();

        OnBackPressedCallback callback = new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(EditTasksMain.this, Home.class));
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public void onNameClick(int index) {

        openEditTaskActivity(index);

    }
    private void openEditTaskActivity(int index)
    {
        Intent i=new Intent(EditTasksMain.this,EditTask.class);
        i.putExtra("Key_Task", index);
        startActivity(i);
        finish();
    }
    private void init()
    {
        manager=getSupportFragmentManager();
        fList=manager.findFragmentById(R.id.fList);
    }

}
