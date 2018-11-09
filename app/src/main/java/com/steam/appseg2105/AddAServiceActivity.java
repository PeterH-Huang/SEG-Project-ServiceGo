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
                addService();
            }
        });
    }
    private void addService(){
        String serviceTitleValue = null;
        Double hourlyWageValue = null;
        try{
             serviceTitleValue = serviceTitle.getText().toString().trim();
             hourlyWageValue = Double.parseDouble(hourlyWage.getText().toString().trim());
        }catch(IllegalArgumentException e){
            Toast.makeText(this, "You can only enter numbers in the wage field.", Toast.LENGTH_LONG).show();
        }
        if((serviceTitleValue==null)||(hourlyWageValue==null)){
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
        }else if(serviceTitleValue.matches(".*\\d+.*")){
            Toast.makeText(this, "Service cannot contain numbers.", Toast.LENGTH_LONG).show();
        }else if(hourlyWageValue<14){
            Toast.makeText(this, "Hourly wage cannot be less than $14.", Toast.LENGTH_LONG).show();
        }else{
            addToServiceModel(serviceTitleValue,hourlyWageValue);
        }
    }
    private void addToServiceModel(String serviceValue, Double wage) {

            //creates the service in the service model
            Service service = new Service(serviceValue,wage);
            databaseServices.child(serviceValue).setValue(service);
            serviceTitle.setText("");
            hourlyWage.setText("");
            Toast.makeText(this, "Service Creation Successful.", Toast.LENGTH_LONG).show();
            //starts the sign in activity
            startActivity(new Intent(AddAServiceActivity.this, Admin.class));
            finish();
        }}

