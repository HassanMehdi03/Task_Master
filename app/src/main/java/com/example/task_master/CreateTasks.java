package com.example.task_master;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTasks extends AppCompatActivity {

    TextInputEditText etTitle,etDescription,etAddNotes;
    RadioGroup rgPriority;
    RadioButton selectedButton;
    Button btnCreate,btnDatePick;
    int finalYear,finalMonth,finalDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tasks);

        init();

        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c=Calendar.getInstance();

                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                DatePickerDialog datePicker=new DatePickerDialog(CreateTasks.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                        finalYear = selectedYear;
                        finalMonth = selectedMonth + 1; // Month is zero-indexed in Calendar class
                        finalDay = selectedDay;

                    }
                }, year, month, day);

                datePicker.show();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=etTitle.getText().toString().trim();
                String desc=etDescription.getText().toString().trim();

                int selectedID=rgPriority.getCheckedRadioButtonId();
                if(selectedID == -1)
                {
                  Toast.makeText(CreateTasks.this,getString( R.string.please_select_a_priority), Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedButton=findViewById(selectedID);
                String priority=selectedButton.getText().toString();

                String note=etAddNotes.getText().toString();

                if(title.isEmpty())
                {
                    etTitle.setError("This field is required");
                }
                if(desc.isEmpty())
                {
                    etDescription.setError("This field is required");
                }
                if(note.isEmpty())
                {
                    etAddNotes.setError("This field is required");
                }

                if(!title.isEmpty() && !desc.isEmpty() && !note.isEmpty() && !priority.isEmpty())
                {

                    SharedPreferences sPref=getSharedPreferences("user_data",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sPref.edit();
                    editor.putBoolean("Key_isComplete",false);
                    editor.apply();


                    MyApplication.tasks.add(new Task(title,desc,priority,note,finalDay,finalMonth,finalYear));
                    Toast.makeText(CreateTasks.this, getString(R.string.task_created_successfully), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateTasks.this,Home.class));
                    finish();
                }
                else
                {
                    Toast.makeText(CreateTasks.this, getString(R.string.input_field_cannot_be_empty), Toast.LENGTH_SHORT).show();

                }


            }
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(CreateTasks.this, Home.class));
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }


    public void init()
    {
        rgPriority=findViewById(R.id.rgPriority);
        etTitle=findViewById(R.id.etTitle);
        etDescription=findViewById(R.id.etDescription);
        etAddNotes=findViewById(R.id.etAddNotes);
        btnCreate=findViewById(R.id.btnCreate);
        btnDatePick=findViewById(R.id.btnPickDate);
    }

}