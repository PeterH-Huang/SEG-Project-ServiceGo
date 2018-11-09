package com.steam.appseg2105;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin extends AppCompatActivity {
    //Displays the welcome message
    private TextView welcomeMessage;
    //Reference to currently logged in users id
    private String mAuth;
    //reference to the location in the database under their uid
    private DatabaseReference ref;
    private Button addService;
    private Button editService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        editService = findViewById(R.id.editServiceAdmin);
        addService = findViewById(R.id.addService);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        mAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference("users").child(mAuth);
        //Obtains the name of the user from the database and prints in a nicely formatted string
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.child("username").getValue(String.class);
                welcomeMessage.setText("Welcome: "+uName+", you are an Admin");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this,AddAServiceActivity.class));
            }
        });
        editService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this,EditAServiceActivity.class));
            }
        });
        }
    }

