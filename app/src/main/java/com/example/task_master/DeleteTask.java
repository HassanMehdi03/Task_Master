package com.example.task_master;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DeleteTask extends AppCompatActivity implements ListFrag.NameClicked {

    FragmentManager manager;
    Fragment flist;

    Task tasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        init();

        manager.beginTransaction()
                .show(flist).
                commit();

        OnBackPressedCallback callback = new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(DeleteTask.this, Home.class));
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);


    }

    private void init()
    {
        manager=getSupportFragmentManager();
        flist=manager.findFragmentById(R.id.fList);

    }

    @Override
    public void onNameClick(int index) {

    tasks=MyApplication.tasks.get(index);
    showConfirmationDialog();
    }

    private void deleteTask(Task deleteTask)
    {
        MyApplication.tasks.remove(deleteTask);

        ((ListFrag) flist).updateTaskList();

    }

    private void showConfirmationDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");

        builder.setPositiveButton("Yes",(dialog, which) ->
        {
           deleteTask(tasks);
        });

        builder.setNegativeButton("No",(dialog, which) ->
        {

        });

        builder.show();

    }

}