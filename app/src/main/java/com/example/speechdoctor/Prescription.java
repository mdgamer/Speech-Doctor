package com.example.speechdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class Prescription extends AppCompatActivity {
Button btn;
TextView txt,txt1;
String Name="Crocin",Timing="",Pat_Name,Email;
CheckBox cb1,cb2,cb3;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    MedicineGetSet medicineGetSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        btn=(Button)findViewById(R.id.next);
        txt=(TextView)findViewById(R.id.medicine_name1);
        cb1=(CheckBox)findViewById(R.id.morning);
      cb2=(CheckBox)findViewById(R.id.afternoon);
        cb3=(CheckBox)findViewById(R.id.night);
        medicineGetSet=new MedicineGetSet();
        database= FirebaseDatabase.getInstance();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String patientName = preferences.getString("patientName", "");
        final String patientEmail = preferences.getString("patientEmail", "");
        final   SharedPreferences.Editor editor = preferences.edit();
     databaseReference=database.getReference().child("Prescription");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(cb1.isChecked()){
                   Timing=Timing + "Morning ";
               }
               if(cb2.isChecked()){
                    Timing=Timing + "AfterNoon ";
                }
                if(cb3.isChecked()){
                    Timing= Timing +"Night ";
                }
               medicineGetSet.setEmail(patientEmail);
              medicineGetSet.setPat_Name(patientName);
               medicineGetSet.setName(Name);
              medicineGetSet.setTiming(Timing);

                editor.putString("medName",Name);
                editor.putString("timing",Timing);
                editor.apply();

                databaseReference.push().setValue(medicineGetSet);
                Intent i = new Intent(Prescription.this, SignSend.class);
                startActivity(i);
            }
        });
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   if(result.get(0).equals("Crocin")){
                       txt.setText(result.get(0)+" Mg");
                      Name=result.get(0)+" Mg";
                   }

                }
                break;
        }
    }
}
