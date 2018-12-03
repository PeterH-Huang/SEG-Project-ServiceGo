package com.steam.appseg2105;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;


public class SelectAvailabilities extends AppCompatActivity {
    private List<String> hoursList;
    private TextView serviceTitle;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;
    private ListView weeksList;
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


        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> listy = new ArrayList<String>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("username").getValue().toString().equals(getIntent().getStringExtra("name"))){

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
                }else{
                    weekday.setTextColor(defaultColor);
                }
            }
        });
    }
    public void createList(ArrayList list) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        weeksList.setAdapter(adapter);
    }
}
