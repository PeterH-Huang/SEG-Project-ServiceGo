package com.steam.appseg2105;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceProvider extends AppCompatActivity {
    TextView welcomeMessageSP;
    String mAuthSP;
    DatabaseReference refSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        welcomeMessageSP = findViewById(R.id.welcomeMessageSP);
        mAuthSP = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refSP = FirebaseDatabase.getInstance().getReference("users").child(mAuthSP);
        refSP.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.child("username").getValue(String.class);
                welcomeMessageSP.setText("Welcome: "+uName+", you are a Service Provider");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
