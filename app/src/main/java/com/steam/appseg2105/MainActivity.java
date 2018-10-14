package com.steam.appseg2105;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.*;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView adminText = (TextView) findViewById(R.id.adminText);
        Button signUp = (Button) findViewById(R.id.signupButton);
        Button signIn = (Button) findViewById(R.id.signInButton);

    }
}
