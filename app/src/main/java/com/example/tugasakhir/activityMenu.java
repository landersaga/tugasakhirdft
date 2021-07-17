package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class activityMenu extends AppCompatActivity implements View.OnClickListener{

    private TextView button_account;
    private TextView button_settingsAccount;
    private TextView button_about;
    private Button button_logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button_account = findViewById(R.id.button_account);
        button_account.setOnClickListener(this);
        button_settingsAccount = findViewById(R.id.button_settingsAccount);
        button_settingsAccount.setOnClickListener(this);
        button_about = findViewById(R.id.button_about);
        button_about.setOnClickListener(this);
        button_logout = (Button)findViewById(R.id.button_logout);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_account:
                startActivity(new Intent(this, activityAccount.class));
        }
    }
}