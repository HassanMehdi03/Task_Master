package com.example.task_master;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    TextInputEditText etTitle, etDescription, etNotes;
    RadioGroup rgPriority;
    RadioButton rbLow, rbMedium, rbHigh;
    Button btnSave,btnDatePick;

    int finalYear,finalMonth,finalDay;
    Task tasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        int index=getIntent().getIntExtra("Key_Task",-1);
        tasks=MyApplication.tasks.get(index);

        init();

        if(index!=-1)
        {
            putDetailsInFields();
        }
        else
        {
            finish();
        }

        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c=Calendar.getInstance();

                int day=c.get(Calendar.DAY_OF_MONTH);
                int month=c.get(Calendar.MONTH);
                int year=c.get(Calendar.YEAR);

                DatePickerDialog datePicker=new DatePickerDialog(EditTask.this, new DatePickerDialog.OnDateSetListener() {
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

        OnBackPressedCallback callback = new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(EditTask.this, EditTasksMain.class));
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);


    }

    private void putDetailsInFields()
    {
        etTitle.setText(tasks.getTitle());
        etDescription.setText(tasks.getDescription());
        etNotes.setText(tasks.getNotes());

        switch (tasks.getPriority())
        {
            case "Low":
                rbLow.setChecked(true);
                break;
            case "Medium":
                rbMedium.setChecked(true);
                break;
            case "High":
                rbHigh.setChecked(true);
                break;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=etTitle.getText().toString().trim();
                String desc=etDescription.getText().toString().trim();
                String notes=etNotes.getText().toString().trim();
                String selectedPriority=getSelectedPriority();

                if (title.isEmpty() || desc.isEmpty() || notes.isEmpty()) {
                    Toast.makeText(EditTask.this, R.string.please_fill_in_all_the_fields, Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(EditTask.this);
                builder.setTitle("Edit Task");
                builder.setMessage("Do you really want to edit the task?");
                builder.setPositiveButton("Yes",(dialog, which) ->
                {
                    tasks.setTitle(title);
                    tasks.setDescription(desc);
                    tasks.setNotes(notes);
                    tasks.setPriority(selectedPriority);
                    tasks.setDay(finalDay);
                    tasks.setMonth(finalMonth);
                    tasks.setYear(finalYear);

                    Toast.makeText(EditTask.this, getString(R.string.task_edit_successfully), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(EditTask.this,Home.class));
                });

                builder.setNegativeButton("No",(dialog, which) ->
                {
                   finish();
                });

                builder.show();

            }
        });

    }

    private String getSelectedPriority()
    {
        int selectedId=rgPriority.getCheckedRadioButtonId();

        if(selectedId==R.id.rbLow)
        {
            return "Low";
        }
        else if(selectedId==R.id.rbMedium)
        {
            return "Medium";
        }
        else if(selectedId==R.id.rbHigh)
        {
            return "High";
        }

        return "";
    }
    private void init()
    {
        etTitle=findViewById(R.id.etTitle);
        etDescription=findViewById(R.id.etDescription);
        etNotes=findViewById(R.id.etAddNotes);
        btnSave=findViewById(R.id.btnSave);
        btnDatePick=findViewById(R.id.btnPickDate);
        rgPriority=findViewById(R.id.rgPriority);
        rbLow=findViewById(R.id.rbLow);
        rbMedium=findViewById(R.id.rbMedium);
        rbHigh=findViewById(R.id.rbHigh);
        finalDay=tasks.getDay();
        finalMonth=tasks.getMonth();
        finalYear=tasks.getYear();

    }

}