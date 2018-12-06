package com.steam.appseg2105;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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


public class SelectAvailabilities extends AppCompatActivity {
    private ArrayList<TextView> hoursList = new ArrayList<>();
    private TextView serviceTitle;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;
    private ListView weeksList;
    private Button confirmService;
    private DatabaseReference databaseServices;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_availabilities);
        serviceTitle = findViewById(R.id.serviceTitle);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        weeksList = findViewById(R.id.weeksList);
        confirmService = findViewById(R.id.confirmService);


        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final ArrayList<String> listy = new ArrayList<String>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(getIntent().getStringExtra("name"))){
                        final String providerUid = snapshot.getKey();

                        for(final DataSnapshot availSnapshot:snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).getChildren()){
                            listy.add(availSnapshot.getKey());
                        }
                        createList(listy);
                        serviceTitle.setText(getIntent().getStringExtra("service")+" Hours");
                        weeksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final String week = (String) adapterView.getItemAtPosition(i);
                                int defaultColor = serviceTitle.getCurrentTextColor();
                                monday.setText("Monday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("monday").getValue().toString());
                                tuesday.setText("Tuesday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("tuesday").getValue().toString());
                                wednesday.setText("Wednesday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("wednesday").getValue().toString());
                                thursday.setText("Thursday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("thursday").getValue().toString());
                                friday.setText("Friday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("friday").getValue().toString());
                                saturday.setText("Saturday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("saturday").getValue().toString());
                                sunday.setText("Sunday: " + snapshot.child("Availabilities").child(getIntent().getStringExtra("service")).child(week).child("sunday").getValue().toString());
                                selectWeek(monday, defaultColor);
                                selectWeek(tuesday, defaultColor);
                                selectWeek(wednesday,defaultColor);
                                selectWeek(thursday,defaultColor);
                                selectWeek(friday,defaultColor);
                                selectWeek(saturday,defaultColor);
                                selectWeek(sunday,defaultColor);
                                confirmService.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Availability availabilitiesChosen = new Availability(null,null,null,null,null,null,null);
                                        for(int i=0;i<hoursList.size();i++){
                                            if(hoursList.get(i).getText().toString().substring(0,6).equals("Monday")){
                                                availabilitiesChosen.setMonday(monday.getText().toString());
                                            }if(hoursList.get(i).getText().toString().substring(0,7).equals("Tuesday")){
                                                availabilitiesChosen.setTuesday(tuesday.getText().toString());
                                            }if(hoursList.get(i).getText().toString().substring(0,9).equals("Wednesday")){
                                                availabilitiesChosen.setWednesday(wednesday.getText().toString());
                                            }if(hoursList.get(i).getText().toString().substring(0,8).equals("Thursday")){
                                                availabilitiesChosen.setThursday(thursday.getText().toString());
                                            }if(hoursList.get(i).getText().toString().substring(0,6).equals("Friday")){
                                                availabilitiesChosen.setFriday(friday.getText().toString());
                                            }if(hoursList.get(i).getText().toString().substring(0,8).equals("Saturday")){
                                                availabilitiesChosen.setSaturday(saturday.getText().toString());
                                            }if(hoursList.get(i).getText().toString().substring(0,6).equals("Sunday")){
                                                availabilitiesChosen.setSunday(sunday.getText().toString());
                                            }
                                        }
                                        databaseServices = FirebaseDatabase.getInstance().getReference("users");
                                            databaseServices.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Services Booked").child(getIntent().getStringExtra("name")).child(getIntent().getStringExtra("service")).child(week).setValue(availabilitiesChosen);
                                            databaseServices.child(providerUid).child("Services Booked").child(week).child(getIntent().getStringExtra("service")).child(    dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").getValue().toString()).setValue(availabilitiesChosen);
                                            startActivity(new Intent(SelectAvailabilities.this, HomeOwner.class));
                                        Toast.makeText(SelectAvailabilities.this, "Successfully chose availabilities ", Toast.LENGTH_LONG).show();



                                    }
                                });
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void selectWeek(final TextView weekday, final int defaultColor){
        weekday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(weekday.getCurrentTextColor()==defaultColor){
                    weekday.setTextColor(Color.RED);
                    hoursList.add(weekday);
                }else{
                    weekday.setTextColor(defaultColor);
                    hoursList.remove(weekday);
                }
            }
        });
    }
    public void createList(ArrayList list) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        weeksList.setAdapter(adapter);
    }
}
