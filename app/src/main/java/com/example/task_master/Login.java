package com.example.task_master;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Login extends AppCompatActivity {

    TextInputEditText etEmail,etPassword;
    Button btnLogin,btnCancel;

    TextView tvSignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        String email = sharedPreferences.getString("Key_Email", "");
        String password = sharedPreferences.getString("Key_Password", "");

        init();

        if(sharedPreferences.getBoolean("Key_isLogin",false))
        {
            startActivity(new Intent(Login.this,Home.class));
        }
        else
        {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String userEmail=etEmail.getText().toString().trim();
                    String userPassword=etPassword.getText().toString().trim();

                    if(userEmail.isEmpty() || userPassword.isEmpty())
                    {
                        Toast.makeText(Login.this, getString(R.string.input_field_cannot_be_empty), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(userEmail.equals(email) && userPassword.equals(password))
                        {
                            editor.putBoolean("Key_isLogin",true);
                            editor.apply();
                            Intent i=new Intent(Login.this, Home.class);
                            startActivity(i);
                            finish();


                        }
                        else
                        {
                            Toast.makeText(Login.this, getString(R.string.wrong_email_or_password), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            tvSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,SignUp.class));
                }
            });

        }

        OnBackPressedCallback callBack=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                finishAffinity();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this,callBack);

    }

    private void init()
    {
        btnLogin=findViewById(R.id.btnLogin);
        btnCancel=findViewById(R.id.btnCancel);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        tvSignup=findViewById(R.id.tvSignup);

    }
}