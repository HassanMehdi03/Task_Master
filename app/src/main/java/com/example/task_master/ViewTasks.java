package com.example.task_master;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class ViewTasks extends AppCompatActivity implements ListFrag.NameClicked {


    SharedPreferences sPref;
    SharedPreferences.Editor editor;
    LinearLayout portrait,landscape;
    FragmentManager manager;
    Fragment fList,fDetail;
    View viewOfDetailFrag;
    TextView tvTaskInfo;
    Button btnComplete;
    Task tasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        init();

        if(portrait!=null)
        {
            manager.beginTransaction()
                    .show(fList)
                    .hide(fDetail)
                    .addToBackStack(null)
                    .commit();
        }
        else
        {
            manager.beginTransaction()
                    .show(fList)
                    .hide(fDetail)
                    .addToBackStack(null)
                    .commit();
        }

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putBoolean("Key_isComplete",true);
                editor.apply();

                Toast.makeText(ViewTasks.this, getString(R.string.task_marked_as_completed), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ViewTasks.this,ViewTasks.class));


            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(ViewTasks.this, Home.class));
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public void onNameClick(int index) {

        tasks=MyApplication.tasks.get(index);

        StringBuilder taskInfo = new StringBuilder();
        taskInfo.append("Title: ").append(tasks.getTitle()).append("\n");
        taskInfo.append("Description: ").append(tasks.getDescription()).append("\n");
        taskInfo.append("Additional Notes: ").append(tasks.getNotes()).append("\n");
        taskInfo.append("Priority: ").append(tasks.getPriority()).append("\n");
        taskInfo.append("Due Date: ").append(tasks.getDay()).append("/").append(tasks.getMonth()).append("/").append(tasks.getYear()).append("\n");
        taskInfo.append("Completion Status: ").append(sPref.getBoolean("Key_isComplete", false) ? "Completed" : "Incomplete").append("\n");

        tvTaskInfo.setText(taskInfo.toString());

        if(portrait!=null)
        {
            manager.beginTransaction()
            .show(fDetail)
            .hide(fList)
            .addToBackStack(null)
            .commit();

        }
        else
        {
            manager.beginTransaction()
                    .show(fDetail)
                    .show(fList)
                    .addToBackStack(null)
                    .commit();

        }


    }

    private void init()
    {
        manager=getSupportFragmentManager();
        fList=manager.findFragmentById(R.id.fList);
        fDetail=manager.findFragmentById(R.id.fContent);
        viewOfDetailFrag=fDetail.getView();
        tvTaskInfo=viewOfDetailFrag.findViewById(R.id.tvTaskInfo);
        portrait=findViewById(R.id.portrait_mode);
        landscape=findViewById(R.id.landscape_mode);
        btnComplete=viewOfDetailFrag.findViewById(R.id.btnComplete);
        sPref=getSharedPreferences("user_data",MODE_PRIVATE);
        editor=sPref.edit();

    }

}