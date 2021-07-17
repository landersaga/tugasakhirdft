package com.example.tugasakhir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activityLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView b_forgotpassword;
    private TextView b_register;
    private Button b_login;
    private EditText text_email, text_password;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        text_email = (EditText) findViewById(R.id.text_email);
        text_password = (EditText) findViewById(R.id.text_password);
        b_register = (TextView) findViewById(R.id.b_register);
        b_register.setOnClickListener(this);
        b_forgotpassword = (TextView) findViewById(R.id.b_forgotpassword);
        b_forgotpassword.setOnClickListener(this);
        b_login = (Button) findViewById(R.id.b_login);
        b_login.setOnClickListener(this);

    }

    private void userLogin() {

        String email, password;
        email = text_email.getText().toString();
        password = text_password.getText().toString();

        if (email.isEmpty()){
            text_email.setError("Email can't be empty!");
            text_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            text_email.setError("Please input email correctly!");
            text_email.requestFocus();
        }

        if (password.isEmpty()){
            text_password.setError("Password can't be empty!");
            text_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(activityLogin.this, activityMain.class));;
                    finish();
                    text_email.setText("");
                    text_password.setText("");
                } else {
                    Toast.makeText(activityLogin.this, "Email or Password incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b_forgotpassword:
                startActivity(new Intent(this, activityForgotPassword.class));
                break;
            case R.id.b_register:
                startActivity(new Intent(this, activityRegister.class));
                break;
            case R.id.b_login:
                userLogin();
        }
    }
}
