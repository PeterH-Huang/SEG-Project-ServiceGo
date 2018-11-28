package com.steam.appseg2105;

import android.content.Intent;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeOwner extends AppCompatActivity {
    //Displays the welcome message
    private TextView welcomeMessageHO;
    //Reference to currently logged in users id
    private String mAuthHO;
    //reference to the location in the database under their uid
    private DatabaseReference refHO;
    private Button rating;
    private ArrayList<String> arr;
    private Spinner spinner;
    private Button bookService;
    private EditText editRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);
        welcomeMessageHO = findViewById(R.id.welcomeMessageHO);
        arr = new ArrayList<>();
        rating = findViewById(R.id.subRating);
        editRating = findViewById(R.id.serviceProviderRating);
        arr.add("1");
        arr.add("2");
        arr.add("3");
        arr.add("4");
        arr.add("5");
        spinner = findViewById(R.id.spinner2); //retrieve the spinner from the xml
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arr);// Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        spinner.setAdapter(adapter);
        mAuthHO = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refHO = FirebaseDatabase.getInstance().getReference("users").child(mAuthHO);
        //Obtains the name of the user from the database and prints in a nicely formatted string
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
            bookService = findViewById(R.id.bookService);
            bookService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(HomeOwner.this, BookAService.class));
                }
            });
            rating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final double rating = Double.parseDouble(String.valueOf(spinner.getSelectedItem()));
                    final String sP = editRating.getText().toString();
                    FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if(snapshot.child("username").getValue().toString().equals(sP)){
                                    final DatabaseReference dB = FirebaseDatabase.getInstance().getReference().child("users").child(snapshot.getKey());
                                dB.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.child("numRatings").exists()){
                                            int numPresent = Integer.parseInt(dataSnapshot.child("numRatings").getValue().toString());
                                            dB.child("numRatings").setValue(numPresent+1);
                                            dB.child("rating").setValue((Double.parseDouble(dataSnapshot.child("rating").getValue().toString())*(((double) numPresent/((double) numPresent+1.0))))+(rating*(1.0/((double) numPresent+1.0))));
                                            Toast.makeText(HomeOwner.this, "Successfully rated", Toast.LENGTH_LONG).show();
                                        }else{
                                                dB.child("numRatings").setValue(rating);
                                                Toast.makeText(HomeOwner.this, "Successfully rated", Toast.LENGTH_LONG).show();
                                            }
                                        }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
            });
        }
    }

