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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                deleteService();
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

    private void deleteServiceModel(final String serviceValue) {
            databaseServices.child(serviceValue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseServices.child(serviceValue).removeValue();
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(DeleteAServiceActivity.this, "Service does not exist.", Toast.LENGTH_LONG).show();
                    finish();
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

