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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activityRegister extends AppCompatActivity implements View.OnClickListener {

    private EditText text_nama, text_contact, text_address, text_email, text_password;
    private Button b_register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        text_nama = (EditText)findViewById(R.id.text_nama);
        text_contact = (EditText)findViewById(R.id.text_contact);
        text_address = (EditText)findViewById(R.id.text_address);
        text_email = (EditText)findViewById(R.id.text_email);
        text_password = (EditText)findViewById(R.id.text_password);
        b_register = (Button)findViewById(R.id.b_register);
        b_register.setOnClickListener(this);
    }

    public void onClick (View v){
        switch (v.getId()){
            case R.id.b_register:
                b_register();
                break;
        }
    }

    //register user baru
    private void b_register(){
        final String name = text_nama.getText().toString().trim();
        final String contact = text_contact.getText().toString().trim();
        final String address = text_address.getText().toString().trim();
        final String email = text_email.getText().toString().trim();
        final String password = text_password.getText().toString().trim();

        if (name.isEmpty()){
            text_nama.setError("Name is required!");
            text_nama.requestFocus();
            return;
        }

        if (contact.isEmpty()){
            text_contact.setError("Contact is required!");
            text_nama.requestFocus();
            return;
        }

        if (address.isEmpty()){
            text_address.setError("Address is required!");
            text_nama.requestFocus();
            return;
        }

        if (email.isEmpty()){
            text_email.setError("Email is required!");
            text_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            text_email.setError("Please provide valid email!");
            text_email.requestFocus();
        }

        if (password.isEmpty()){
            text_password.setError("Password is required!");
            text_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, contact, address, email, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(activityRegister.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(activityRegister.this, "Registration failed!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(activityRegister.this, "Registration failed!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
}
    }