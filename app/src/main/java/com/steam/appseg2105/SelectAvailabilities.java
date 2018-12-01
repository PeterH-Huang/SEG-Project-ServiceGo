package com.steam.appseg2105;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SelectAvailabilities extends AppCompatActivity {
    private ListView availList;
    private List<String> serviceList;
    private TextView mondayToFriday;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;
    private ListView weeksList;
    private TextView testt;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_availabilities);
        mondayToFriday = findViewById(R.id.mondayToFriday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        weeksList = findViewById(R.id.weeksList);
        testt = findViewById(R.id.test);

        //final DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().get.getUid()).child("Availabilities").child(getIntent().getStringExtra("item"));
        //final DatabaseReference name = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities");

        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> listy = new ArrayList<String>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //testt.setText(snapshot.child("username").getValue().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(snapshot.getKey()).child("Availabilities").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(getIntent().getStringExtra("item")).exists()) {
                                testt.setText(dataSnapshot.child("username").getValue().toString());
                                listy.add(snapshot.child("username").getValue().toString());
                            }
                            createList(listy);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



/*
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> weekArray = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String week = snapshot.getKey().trim();
                    weekArray.add(week);
                    mondayToFriday.setText(name.child(getIntent().getStringExtra("item")).getKey()+" Hours");

                }
                ArrayAdapter adapter = new ArrayAdapter(SelectAvailabilities.this, android.R.layout.simple_list_item_1, weekArray);
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
                                Toast.makeText(SelectAvailabilities.this, "Done", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


    public void createList(ArrayList list) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        weeksList.setAdapter(adapter);
    }
}
