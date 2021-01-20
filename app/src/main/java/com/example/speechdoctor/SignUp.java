package com.example.speechdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
Button signup;
EditText name,phn,pass,email;
TextView txt;
ProgressBar progressBar;
FirebaseAuth firebaseAuth;
FirebaseDatabase database;
DatabaseReference databaseReference;
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup=(Button)findViewById(R.id.signupbtn);
        name=(EditText)findViewById(R.id.name);
        phn=(EditText)findViewById(R.id.phonenumber);
        pass=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        txt=(TextView)findViewById(R.id.txt_signin);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        firebaseAuth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        user=new User();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
     final   SharedPreferences.Editor editor = preferences.edit();

        databaseReference=database.getReference().child("User");
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String mEmail,mName,mPhn,mPass;
                mEmail=email.getText().toString();
                mName=name.getText().toString();
                mPhn=phn.getText().toString();
                mPass=pass.getText().toString();
                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(mPass)){
                    pass.setError("Password is Required");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user.setEmail(mEmail);
                            user.setName(mName);
                            user.setPhone(mPhn);
                            databaseReference.push().setValue(user);




                            editor.putString("doctorName",mName);
                            editor.putString("doctorEmail",mEmail);
                            editor.apply();



                            Toast.makeText(SignUp.this,"User Registered",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(SignUp.this,Home.class);
                            startActivity(in);
                        }
                        else{
                            Toast.makeText(SignUp.this,"User Cannot be Registered"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignUp.this,SignIn.class);
                startActivity(in);
            }
        });
    }
}
