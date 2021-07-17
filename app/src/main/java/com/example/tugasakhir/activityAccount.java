package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activityAccount extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    Button b_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView txt_vnama = (TextView)findViewById(R.id.txt_vnama);
        final TextView txt_vadress = (TextView)findViewById(R.id.txt_vaddress);
        final TextView txt_vcontact = (TextView)findViewById(R.id.txt_vcontact);
        final TextView txt_vemail = (TextView)findViewById(R.id.txt_vemail);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String nama = userProfile.name;
                    String address = userProfile.address;
                    String contact = userProfile.contact;
                    String email = userProfile.email;

                    txt_vnama.setText(nama);
                    txt_vadress.setText(address);
                    txt_vcontact.setText(contact);
                    txt_vemail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activityAccount.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void b_button(View view){
        Intent intent = new Intent(this, activityMain.class);
    }
}