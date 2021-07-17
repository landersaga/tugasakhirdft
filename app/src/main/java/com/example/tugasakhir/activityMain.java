package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class activityMain extends AppCompatActivity implements View.OnClickListener {

    private TextView value_ph;
    private TextView value_ppm;
    private Button button_menu;
    private Firebase mRef;
    private Firebase mRefPPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/pH_value");
        mRefPPM = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/ppm_value");

        value_ph = (TextView)findViewById(R.id.value_ph);
        value_ppm = (TextView)findViewById(R.id.value_ppm);
        button_menu = (Button)findViewById(R.id.button_menu);
        button_menu.setOnClickListener(this);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valuePh = dataSnapshot.getValue(String.class);
                int value_Ph = Integer.parseInt(valuePh);

                if (value_Ph <= 10) {
                    value_ph.setText(String.valueOf(value_Ph));
                    value_ph.setTextColor(Color.RED);
                } else if (value_Ph > 10) {
                    value_ph.setText(String.valueOf(value_Ph));
                    value_ph.setTextColor(getResources().getColor(R.color.GreenMain));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRefPPM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valueppm = dataSnapshot.getValue(String.class);
                int value_Ppm = Integer.parseInt(valueppm);

                if (value_Ppm <= 600) {
                    value_ppm.setText(String.valueOf(value_Ppm));
                    value_ppm.setTextColor(Color.RED);
                } else if (value_Ppm >= 600) {
                    value_ppm.setText(String.valueOf(value_Ppm));
                    value_ppm.setTextColor(getResources().getColor(R.color.GreenMain));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_menu:
                startActivity(new Intent(this, activityMenu.class));
        }
    }
    public void onBackPressed(){
        moveTaskToBack(true);
    }


}