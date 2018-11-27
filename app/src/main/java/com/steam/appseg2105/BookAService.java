package com.steam.appseg2105;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookAService extends AppCompatActivity {
    private Spinner spinner;
    private List<String> serviceList;
    private EditText userInput;
    private ListView listView;
    private Button serviceClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_aservice);
        serviceList = new ArrayList<>();
        serviceClick = findViewById(R.id.serviceClick);
        listView = findViewById(R.id.listBook);
        userInput = findViewById(R.id.userInput);
        serviceList.add("Search by Service Type");
        serviceList.add("Search by Time");
        serviceList.add("Search by Rating");
        spinner = findViewById(R.id.bookSpin); //retrieve the spinner from the xml
        // Create an ArrayAdapter using the string array and a default spinner layout// Specify the layout to use when the list of choices appears
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, serviceList);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(spinner.getSelectedItem()).equals("Search by Service Type")) {
                    searchByTitle();
                } else if (String.valueOf(spinner.getSelectedItem()).equals("Search by Time")) {
                    searchByTime();
                } else {
                    searchByRating();
                }
            }
        });


    }

    private void searchByRating() {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> listy = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child("rating").getValue() != null) {
                        if (snapshot.child("rating").getValue().equals(userInput.getText().toString())) {
                            String user = snapshot.child("username").getValue().toString();
                            listy.add(user);
                        }
                    }
                }
                createList(listy);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void searchByTitle() {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<String> listy = new ArrayList<String>();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(snapshot.getKey()).child("Availabilities").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(userInput.getText().toString()).exists()) {
                                listy.add(snapshot.child("username").getValue().toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                createList(listy);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchByTime() {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshoty) {
                final ArrayList<String> listy = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshoty.getChildren()) {
                    //make sure there is an account or its going to crash//
                    create(snapshot,listy);
                }
                createList(listy);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void createList(ArrayList list) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    public ArrayList<String> create(final DataSnapshot snapshot, final ArrayList listy) {
        if (snapshot.child("typeOfAccount").getValue().toString().equals("Service Provider")) {
            FirebaseDatabase.getInstance().getReference().child("users").child(snapshot.getKey()).child("Availabilities").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshotServices : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshotDatesTemp : snapshotServices.getChildren()) {
                            for (DataSnapshot snapshotDates : snapshotDatesTemp.getChildren()) {
                                String defaultStart = snapshotDates.getValue().toString().substring(5, 7);
                                String defaultEnd = snapshotDates.getValue().toString().substring(13, 15);
                                String userStart = userInput.getText().toString().substring(6, 8);
                                String userEnd = userInput.getText().toString().substring(17);
                                if (defaultStart.equals("AM") && defaultEnd.equals("AM") && userStart.equals("AM") && userEnd.equals("AM")) {
                                    if (Integer.parseInt(userInput.getText().toString().substring(0, 2)) >= Integer.parseInt(snapshotDates.getValue().toString().substring(0, 2)) && Integer.parseInt(snapshotDates.getValue().toString().substring(8, 10)) >= Integer.parseInt(userInput.getText().toString().substring(11, 13))) {
                                        listy.add(snapshot.child("username").getValue().toString() + " " + snapshotDatesTemp.getKey()+" "+snapshotDates.getKey());
                                    }
                                } else if (defaultStart.equals("AM") && defaultEnd.equals("PM") && userStart.equals("AM") && userEnd.equals("AM")) {
                                    int realEnd = Integer.parseInt(snapshotDates.getValue().toString().substring(8, 10)) + 12;
                                    if (Integer.parseInt(userInput.getText().toString().substring(0, 2)) >= Integer.parseInt(snapshotDates.getValue().toString().substring(0, 2)) && Integer.parseInt(userInput.getText().toString().substring(11, 13)) <= realEnd) {
                                        listy.add(snapshot.child("username").getValue().toString() + " " + snapshotDatesTemp.getKey()+" "+snapshotDates.getKey());
                                    }
                                } else if (defaultStart.equals("AM") && defaultEnd.equals("PM") && userStart.equals("AM") && userEnd.equals("PM")) {
                                    if (Integer.parseInt(userInput.getText().toString().substring(0, 2)) >= Integer.parseInt(snapshotDates.getValue().toString().substring(0, 2)) && Integer.parseInt(snapshotDates.getValue().toString().substring(8, 10)) >= Integer.parseInt(userInput.getText().toString().substring(11, 13))) {
                                        listy.add(snapshot.child("username").getValue().toString() + " " + snapshotDatesTemp.getKey()+" "+snapshotDates.getKey());
                                    }
                                } else if (defaultStart.equals("AM") && defaultEnd.equals("PM") && userStart.equals("PM") && userEnd.equals("PM")) {
                                    if (Integer.parseInt(snapshotDates.getValue().toString().substring(8, 10)) >= Integer.parseInt(userInput.getText().toString().substring(11, 13))) {
                                        listy.add(snapshot.child("username").getValue().toString() + " " + snapshotDatesTemp.getKey()+" "+snapshotDates.getKey());
                                    }
                                } else if (defaultStart.equals("PM") && defaultEnd.equals("PM") && userStart.equals("PM") && userEnd.equals("PM")) {
                                    if (Integer.parseInt(snapshotDates.getValue().toString().substring(0, 2)) >= Integer.parseInt(userInput.getText().toString().substring(0, 2)) && Integer.parseInt(snapshotDates.getValue().toString().substring(8, 10)) >= Integer.parseInt(userInput.getText().toString().substring(11, 13))) {
                                        listy.add(snapshot.child("username").getValue().toString() + " " + snapshotDatesTemp.getKey()+" "+snapshotDates.getKey());
                                    }
                                }
                            }
                        }
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return listy;
        }

        return null;
    }}


