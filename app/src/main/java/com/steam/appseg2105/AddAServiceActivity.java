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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAServiceActivity extends AppCompatActivity {
    EditText serviceTitle;
    EditText hourlyWage;
    Button submitService;
    private DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aservice);
        serviceTitle = findViewById(R.id.serviceTitle);
        hourlyWage = findViewById(R.id.hourlyWage);
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        submitService = findViewById(R.id.addServiceBut);
        submitService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToServiceModel();
            }
        });
    }
    private void addToServiceModel() {
        String serviceTitleValue = serviceTitle.getText().toString().trim();
        Double hourlyWageValue = Double.parseDouble(hourlyWage.getText().toString().trim());
            //creates the service in the service model
            Service service = new Service(serviceTitleValue,hourlyWageValue);
            databaseServices.child(serviceTitleValue).setValue(service);
            serviceTitle.setText("");
            hourlyWage.setText("");
            Toast.makeText(this, "Service Creation Successful.", Toast.LENGTH_LONG).show();
            //starts the sign in activity
            startActivity(new Intent(AddAServiceActivity.this, Admin.class));
            finish();
        }}

