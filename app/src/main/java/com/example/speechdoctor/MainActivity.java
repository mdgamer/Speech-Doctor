package com.example.speechdoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button signin,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    signin=(Button)findViewById(R.id.signin);
    signup=(Button)findViewById(R.id.signup);

    signin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent in = new Intent(MainActivity.this,SignIn.class);
            startActivity(in);
        }
    });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this,SignUp.class);
                startActivity(in);

            }
        });
    }
}
