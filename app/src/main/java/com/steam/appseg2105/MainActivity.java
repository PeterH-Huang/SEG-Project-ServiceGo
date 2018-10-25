package com.steam.appseg2105;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Class where the user can choose to sign in or sign up
public class MainActivity extends AppCompatActivity {

    private Button signInButtonMain; //BUTTON LABELLED SIGN IN
    private Button signUpButtonMain; //BUTTON LABELLED SIGN UP
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButtonMain = findViewById(R.id.signInButtonMain); //retrieve THE XML SIGN IN BUTTON
        signUpButtonMain = findViewById(R.id.signUpButtonMain); //retrieve THE XML SIGN UP BUTTON
        //starts the signup activity on button click
        signUpButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });
        //starts the signin activity on click
        signInButtonMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignIn.class));
            }
        });
        }
    }




