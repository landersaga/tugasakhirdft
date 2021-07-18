package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activityMain extends AppCompatActivity implements View.OnClickListener {

    private TextView value_ph;
    private TextView value_ppm;
    private Button button_menu;
    private Button button_lamp;
    private Firebase mRefpH;
    private Firebase mRefPPM;
    private Firebase mRefLED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefpH = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/pH_value");
        mRefPPM = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/ppm_value");
        mRefLED = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/led_status");

        value_ph = (TextView)findViewById(R.id.value_ph);
        value_ppm = (TextView)findViewById(R.id.value_ppm);
        button_lamp = (Button)findViewById(R.id.button_lamp);
        button_menu = (Button)findViewById(R.id.button_menu);
        button_menu.setOnClickListener(this);

        //Code untuk mengidentifikasi nilai pH dari Firebase
        mRefpH.addValueEventListener(new ValueEventListener() {
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

        //Code untuk mengidentifikasi nilai ppm dari Firebase
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

        //Code untuk mati/nyalain lampu led
        mRefLED.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledstatus = dataSnapshot.getValue(String.class);
                int led_status = Integer.parseInt(ledstatus);

                if (led_status == 0){
                    button_lamp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("led_status");

                            myRef.setValue(1);
                            button_lamp.setBackground(getResources().getDrawable(R.drawable.button_lamp_mainblue));

                        }
                    });
                }else if (led_status == 1){
                    button_lamp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("led_status");

                            myRef.setValue(0);
                            button_lamp.setBackground(getResources().getDrawable(R.drawable.button_lamp));

                        }
                    });
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