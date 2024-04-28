package com.example.task_master;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class SignUp extends AppCompatActivity {


    TextInputEditText etFName,etLName,etEmail,etPassword,etCPassword;
    Button btnRegister,btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(SignUp.this,Login.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName=etFName.getText().toString().trim();
                String lName=etLName.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String password=etPassword.getText().toString();
                String cPassword=etCPassword.getText().toString();

                if(fName.isEmpty())
                {
                    etFName.setError("This field is required");
                }
                if(lName.isEmpty())
                {
                    etLName.setError("This field is required");
                }
                if(email.isEmpty())
                {
                    etEmail.setError("This field is required");
                }
                if(password.isEmpty())
                {
                    etPassword.setError("This field is required");
                }
                if(cPassword.isEmpty())
                {
                    etCPassword.setError("This field is required");
                }

                if(!fName.isEmpty() && !lName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !cPassword.isEmpty())
                {
                    if(password.equals(cPassword))
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Key_Email", email);
                        editor.putString("Key_Password", password);
                        editor.apply();

                        startActivity(new Intent(SignUp.this, Login.class));
                        finish();
                    }
                    else
                    {
                        etCPassword.setError("Password not matched");
                    }
                }

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
                Toast.makeText(SignUp.this, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show();
                lastBackPressTime = currentTime;
            }
        }
    };
    private void init()
    {
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etCPassword=findViewById(R.id.etCPassword);
        etFName=findViewById(R.id.etFName);
        etLName=findViewById(R.id.etLName);
        btnRegister=findViewById(R.id.btnRegister);
        btnCancel=findViewById(R.id.btnCancel);

    }
}