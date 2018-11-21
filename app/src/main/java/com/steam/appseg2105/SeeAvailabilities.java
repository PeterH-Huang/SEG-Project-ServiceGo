package com.steam.appseg2105;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeAvailabilities extends AppCompatActivity {
    private TextView mondayToFriday;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;
    private ListView weeksList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_availabilities);
        mondayToFriday = findViewById(R.id.mondayToFriday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        weeksList = findViewById(R.id.weeksList);
        final DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities").child(getIntent().getStringExtra("item"));

        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> weekArray = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String week = snapshot.getKey().trim();
                    weekArray.add(week);
                    mondayToFriday.setText(snapshot.child("serviceTitle").getValue().toString()+" Hours");

                }
                ArrayAdapter adapter = new ArrayAdapter(SeeAvailabilities.this, android.R.layout.simple_list_item_1, weekArray);
                weeksList.setAdapter(adapter);
                weeksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String position = (String) adapterView.getItemAtPosition(i);
                        r.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if(snapshot.getKey().equals(position)) {
                                        monday.setText("Monday: " + snapshot.child("monday").getValue().toString());
                                        tuesday.setText("Tuesday: " + snapshot.child("tuesday").getValue().toString());
                                        wednesday.setText("Wednesday: " + snapshot.child("wednesday").getValue().toString());
                                        thursday.setText("Thursday: " + snapshot.child("thursday").getValue().toString());
                                        friday.setText("Friday: " + snapshot.child("friday").getValue().toString());
                                        saturday.setText("Saturday: " + snapshot.child("saturday").getValue().toString());
                                        sunday.setText("Sunday: " + snapshot.child("sunday").getValue().toString());
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(SeeAvailabilities.this, "Done", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

}
}



