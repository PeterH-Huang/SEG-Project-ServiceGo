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

public class HomeOwner extends AppCompatActivity {
    TextView welcomeMessageHO;
    String mAuthHO;
    DatabaseReference refHO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);
        welcomeMessageHO = findViewById(R.id.welcomeMessageHO);
        mAuthHO = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refHO = FirebaseDatabase.getInstance().getReference("users").child(mAuthHO);
        refHO.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.child("username").getValue(String.class);
                welcomeMessageHO.setText("Welcome: "+uName+", you are a Home Owner");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
