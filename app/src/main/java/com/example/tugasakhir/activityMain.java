package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
    private TextView text_water_tendon;
    private TextView text_water_nutritient;
    private Button button_menu;
    private Button button_lamp;
    private Button button_refresh;
    private Button button_wave;
    private Button button_pump;
    private Firebase mRefpH;
    private Firebase mRefPPM;
    private Firebase mRefLED;
    private Firebase mRefPump;
    private Firebase mRefWaveMaker;
    private Firebase mRefAirTendon;
    private Firebase mRefAirNutrisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRefpH = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/pH_value");
        mRefPPM = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/ppm_value");
        mRefLED = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/led_status");
        mRefPump = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/pump_status");
        mRefWaveMaker = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/wave_status");
        mRefAirTendon = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/air_tendon");
        mRefAirNutrisi = new Firebase("https://tugasakhirdft-default-rtdb.firebaseio.com/air_nutrisi");

        value_ph = (TextView)findViewById(R.id.value_ph);
        value_ppm = (TextView)findViewById(R.id.value_ppm);
        text_water_tendon = (TextView)findViewById(R.id.text_water_tendon);
        text_water_nutritient = (TextView)findViewById(R.id.text_water_nutritient);
        button_lamp = (Button)findViewById(R.id.button_lamp);
        button_wave = (Button)findViewById(R.id.button_wave);
        button_pump = (Button)findViewById(R.id.button_pump);
        button_menu = (Button)findViewById(R.id.button_menu);
        button_menu.setOnClickListener(this);
        button_refresh = (Button)findViewById(R.id.button_refresh);
        button_refresh.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Hydra", "My Hydra", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //Code untuk mengidentifikasi nilai pH dari Firebase
        mRefpH.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valuePh = dataSnapshot.getValue(String.class);
                int value_Ph = Integer.parseInt(valuePh);

                if (value_Ph <= 10) {

                    value_ph.setText(String.valueOf(value_Ph));
                    value_ph.setTextColor(Color.RED);
                    int index = 1;

                    if (index == 1){
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(activityMain.this, "Hydra")
                                .setSmallIcon(R.drawable.logohydra).setContentTitle("Hydroponic Automation")
                                .setContentText("Your pH Level is" + value_Ph)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText("Please be aware of your hydroponic's pH Level is " + value_Ph))
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(activityMain.this);
                        managerCompat.notify(1, builder.build());
                    }


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
                    int index = 1;

                    if (index == 1){
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(activityMain.this, "Hydra")
                                .setSmallIcon(R.drawable.logohydra).setContentTitle("Hydroponic Automation")
                                .setContentText("Your pH Level is" + value_Ppm)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText("Please be aware of your hydroponic's ppm Level is " + value_Ppm))
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(activityMain.this);
                        managerCompat.notify(1, builder.build());
                    }

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

        //Code untuk mati/nyalain pump air tendon
        mRefPump.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pumpstatus = dataSnapshot.getValue(String.class);
                int pump_status = Integer.parseInt(pumpstatus);

                if (pump_status == 0){
                    button_pump.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myref = database.getReference("pump_status");

                            myref.setValue(1);
                            button_pump.setBackground(getResources().getDrawable(R.drawable.button_pump_mainblue));
                        }
                    });
                }else if (pump_status == 1){
                    button_pump.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myref = database.getReference("pump_status");

                            myref.setValue(0);
                            button_pump.setBackground(getResources().getDrawable(R.drawable.button_pump));
                        }
                    });
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Code untuk mati/nyalain wavemaker di tendon nutrisi
        mRefWaveMaker.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String wavemakerstatus = dataSnapshot.getValue(String.class);
                int wavemaker_status = Integer.parseInt(wavemakerstatus);

                if (wavemaker_status == 0){
                    button_wave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myref = database.getReference("wave_status");

                            myref.setValue(1);
                            button_wave.setBackground(getResources().getDrawable(R.drawable.button_wave_mainblue));
                        }
                    });
                }else if (wavemaker_status == 1){
                    button_wave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myref = database.getReference("wave_status");

                            myref.setValue(0);
                            button_wave.setBackground(getResources().getDrawable(R.drawable.button_wave));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //monitoring air pada tendon
        mRefAirTendon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String air_tenton = dataSnapshot.getValue(String.class);
                int airTendon = Integer.parseInt(air_tenton);

                if (airTendon <=20 && airTendon >= 15){
                    text_water_tendon.setText("Good");
                }else if (airTendon <= 14 && airTendon >=9){
                    text_water_tendon.setText("Enough");
                }else if (airTendon <= 9){
                    text_water_tendon.setText("Bad");
                    text_water_tendon.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //monitoring air pada nutrisi
        mRefAirNutrisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String air_nutrisi = dataSnapshot.getValue(String.class);
                int airNutrisi = Integer.parseInt(air_nutrisi);

                if (airNutrisi <= 11 && airNutrisi >= 8){
                    text_water_nutritient.setText("Good");
                }else if(airNutrisi <= 7 && airNutrisi >= 4){
                    text_water_nutritient.setText("Enough");
                }else if(airNutrisi <= 3){
                    text_water_nutritient.setText("Bad");
                    text_water_nutritient.setTextColor(Color.RED);
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
            case R.id.button_refresh:
                Intent reload = new Intent(getApplicationContext(), activityMain.class);
                startActivity(reload);
        }
    }
    public void onBackPressed(){
        moveTaskToBack(true);
    }


}