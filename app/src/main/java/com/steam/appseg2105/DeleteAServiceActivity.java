package com.steam.appseg2105;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteAServiceActivity extends AppCompatActivity {
    EditText serviceTitle;
    Button deleteService;
    private DatabaseReference databaseServices;
    private DatabaseReference deleteRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_aservice);
        serviceTitle = findViewById(R.id.serviceTitle);
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        deleteService = findViewById(R.id.deleteServiceBut);
        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                r.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("typeOfAccount").getValue().equals("Service Provider")){
                            deleteServiceSP();
                        }else{
                            deleteService();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void deleteService() {

        String serviceTitleValue = serviceTitle.getText().toString().trim();
        if (serviceTitleValue.matches(".*\\d+.*")) {
            Toast.makeText(this, "Service cannot contain numbers.", Toast.LENGTH_LONG).show();
        } else {
            deleteServiceModel(serviceTitleValue);
        }
    }
    //special method for deletion of a service for a service provider
    private void deleteServiceSP() {
        final DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //loops through the different services they are associated with
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //if the service is that of which needs to be deleted it is deleted
                    if (snapshot.getKey().equals(serviceTitle.getText().toString())) {
                        r.child(serviceTitle.getText().toString()).removeValue();
                        Toast.makeText(DeleteAServiceActivity.this, "Service deletion from account was successful.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DeleteAServiceActivity.this,ServiceProvider.class));
                        finish();
                    }
                }
                Toast.makeText(DeleteAServiceActivity.this, "This service is not associated with your account", Toast.LENGTH_LONG).show();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteServiceModel(final String serviceValue) {
            databaseServices.child(serviceValue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseServices.child(serviceValue).removeValue();
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(DeleteAServiceActivity.this, "Service does not exist.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DeleteAServiceActivity.this, "Service deletion successful.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(DeleteAServiceActivity.this, Admin.class));
                    serviceTitle.setText("");
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

