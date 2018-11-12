package com.steam.appseg2105;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddAvailabilities extends AppCompatActivity {
    EditText addAvailM;
    EditText addAvailT;
    EditText addAvailW;
    EditText addAvailThurs;
    EditText addAvailF;
    EditText addAvailS;
    EditText addAvailSun;

    Button submitAvail;
    DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_availabilities);
        addAvailM = findViewById(R.id.MondayA);
        addAvailT = findViewById(R.id.TuesdayA);
        addAvailW = findViewById(R.id.WednesdayA);
        addAvailThurs = findViewById(R.id.ThursdayA);
        addAvailF = findViewById(R.id.FridayA);
        addAvailS = findViewById(R.id.SaturdayA);
        addAvailSun = findViewById(R.id.SundayA);

        submitAvail = findViewById(R.id.submitAvail);
        databaseServices = FirebaseDatabase.getInstance().getReference("users");
        submitAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Availability avail = new Availability(getIntent().getStringExtra("item"),addAvailM.getText().toString().trim(),addAvailT.getText().toString().trim(),addAvailW.getText().toString().trim(),addAvailThurs.getText().toString().trim(),addAvailF.getText().toString().trim(),addAvailS.getText().toString().trim(),addAvailSun.getText().toString().trim());
                databaseServices.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities").child(avail.getServiceTitle()).setValue(avail);
                startActivity(new Intent(AddAvailabilities.this, ServiceProvider.class));
                Toast.makeText(AddAvailabilities.this, "Added new Service Availabilities", Toast.LENGTH_LONG).show();
            }
        });
    }
}
