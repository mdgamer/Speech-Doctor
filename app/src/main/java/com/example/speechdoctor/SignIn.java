package com.example.speechdoctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SignIn extends AppCompatActivity {
Button signin;
EditText pass,email;
TextView txt;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        pass=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        txt=(TextView)findViewById(R.id.txt_signup);
        signin=(Button)findViewById(R.id.signinbtn);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar2);
        firebaseAuth=FirebaseAuth.getInstance();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final   SharedPreferences.Editor editor = preferences.edit();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail,mPass;
                mEmail=email.getText().toString();
                mPass=pass.getText().toString();

                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(mPass)){
                    pass.setError("Password is Required");
                    return;
                }

                editor.putString("doctorEmail",mEmail);
                editor.apply();

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignIn.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(SignIn.this,Home.class);
                            startActivity(in);
                        }
                        else{
                            Toast.makeText(SignIn.this,"Email or Password Incorrect"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignIn.this,SignUp.class);
                startActivity(in);
            }
        });
    }
}
