package com.example.task_master;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {


    TextView tvCreateTask,tvViewTask,tvEditTask,tvDeleteTask;
    Button btnLogout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        tvCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Home.this,CreateTasks.class);
                startActivity(i);
                finish();
            }
        });

        tvViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Home.this,ViewTasks.class);
                startActivity(i);
                finish();

            }
        });

        tvEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             startActivity(new Intent(Home.this, EditTasksMain.class));
             finish();
            }
        });

        tvDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home.this,DeleteTask.class));
                finish();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sPre=getSharedPreferences("user_data",MODE_PRIVATE);
                SharedPreferences.Editor editor=sPre.edit();
                editor.putBoolean("Key_isLogin",false);
                editor.apply();
                startActivity(new Intent(Home.this,Login.class));
                finish();
            }
        });

        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private static final long DOUBLE_PRESS_DELAY = 2000; // 2 seconds
    private long lastBackPressTime = 0;

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastBackPressTime < DOUBLE_PRESS_DELAY) {
                // Double press detected, exit the app
                finishAffinity(); // it will finish all activities and e
            } else
            {
                // Single press, show a toast message
                Toast.makeText(Home.this, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show();
                lastBackPressTime = currentTime;
            }
        }
    };

    private void init()
    {
        tvCreateTask=findViewById(R.id.tvCreateTask);
        tvViewTask=findViewById(R.id.tvViewTask);
        tvEditTask=findViewById(R.id.tvEditTask);
        tvDeleteTask=findViewById(R.id.tvDeleteTask);
        btnLogout=findViewById(R.id.btnLogout);

    }

}