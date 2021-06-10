package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class activityForgotPassword extends AppCompatActivity {

    private EditText text_forgotemail;
    private Button b_reset;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password3);

        text_forgotemail = (EditText)findViewById(R.id.text_forgotemail);
        b_reset = (Button)findViewById(R.id.b_reset);

        auth = FirebaseAuth.getInstance();

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = text_forgotemail.getText().toString().trim();

        if(email.isEmpty()){
            text_forgotemail.setError("Email is required!");
            text_forgotemail.requestFocus();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            text_forgotemail.setError("Please provide valid email!");
            text_forgotemail.requestFocus();
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(activityForgotPassword.this, "Please check your inputed email address to reset password!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(activityForgotPassword.this, "Something wrong, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}