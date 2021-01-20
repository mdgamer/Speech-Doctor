package com.example.speechdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignSend extends AppCompatActivity {
Button signSend;
TextView medicine_name,timing;
DatabaseReference databaseReference; FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_send);

        signSend=(Button)findViewById(R.id.sign_send);
        medicine_name=(TextView)findViewById(R.id.medicine_name_pres);
        timing=(TextView)findViewById(R.id.medicine_time_pres);
        database= FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Prescription");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String medicine = preferences.getString("medName", "");
        final String timing1 = preferences.getString("timing", "");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                medicine_name.setText(medicine);
                timing.setText(timing1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        signSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignSend.this, Finish.class);
                startActivity(i);
            }
        });

    }
}
