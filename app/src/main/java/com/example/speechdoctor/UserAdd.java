package com.example.speechdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAdd extends AppCompatActivity {
Button btn;
EditText name,age,email,phone;
RadioGroup radioGroup;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String gender1="";
    UserAddGetSet userAddGetSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        btn=(Button)findViewById(R.id.btn_user_add);
        radioGroup=(RadioGroup)findViewById(R.id.radio) ;
        radioGroup.clearCheck();
        database= FirebaseDatabase.getInstance();
        userAddGetSet = new UserAddGetSet();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       final SharedPreferences.Editor editor = preferences.edit();


        databaseReference=database.getReference().child("Patient");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mName,mAge,mPhone,mEmail;
                name=findViewById(R.id.user_add_name);
                age=findViewById(R.id.user_add_age);
                phone=findViewById(R.id.user_add_phone);
                email=findViewById(R.id.user_add_email);

                mName=name.getText().toString();
                mAge=age.getText().toString();
                mPhone=phone.getText().toString();
                mEmail=email.getText().toString();
                int h=radioGroup.getCheckedRadioButtonId();
                if(h !=-1){
                    RadioButton jb=(RadioButton)findViewById(h);
                    gender1=jb.getText().toString();
                }

                userAddGetSet.setAge(mAge);
                userAddGetSet.setName(mName);
                userAddGetSet.setEmail(mEmail);
                userAddGetSet.setGender(gender1);
                userAddGetSet.setPhone(mPhone);

                editor.putString("patientName",mName);
                editor.putString("patientEmail",mEmail);
                editor.apply();

                databaseReference.push().setValue(userAddGetSet);
                Toast.makeText(UserAdd.this,"Patient Registered",Toast.LENGTH_SHORT).show();
                Intent in = new Intent(UserAdd.this,Prescription.class);
                startActivity(in);

            }
        });
    }
}
