package com.steam.appseg2105;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceProvider extends AppCompatActivity {
    //Displays the welcome message
    private TextView welcomeMessageSP;
    //Reference to currently logged in users id
    private String mAuthSP;
    //reference to the location in the database under their uid
    private DatabaseReference refSP;
    private ListView listView;
    private Button completeProfile;
    private DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        listView = findViewById(R.id.list);
        completeProfile = findViewById(R.id.completeProfile);
        databaseServices = FirebaseDatabase.getInstance().getReference("services");
        welcomeMessageSP = findViewById(R.id.welcomeMessageSP);
        mAuthSP = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refSP = FirebaseDatabase.getInstance().getReference("users").child(mAuthSP);
        //Obtains the name of the user from the database and prints in a nicely formatted string
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
        operations();
    }
    @Override
    protected void onStart() {
        super.onStart();
        operations();
    }
    public void operations(){
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("profile completed?").getValue().toString().equals("yes")) {
                    databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final ArrayList<String> listy = new ArrayList<String>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String serviceTitle = snapshot.getKey().trim();
                                listy.add(serviceTitle);
                            }
                            createList(listy);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    completeProfile.setVisibility(View.VISIBLE);
                    completeProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(ServiceProvider.this, CompleteProfile.class));
                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void createList(ArrayList listy){
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listy);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);
                    DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities");
                    r.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(item).getValue() == null){
                                Intent intent = new Intent(getApplicationContext(), AddAvailabilities.class);
                                intent.putExtra("item", item);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getApplicationContext(), SeeAvailabilities.class);
                                intent.putExtra("item", item);
                                startActivity(intent);
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

