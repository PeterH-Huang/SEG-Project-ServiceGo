package com.steam.appseg2105;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;

public class AddAvailabilities extends AppCompatActivity {
    LinearLayout mondayLeft;
    LinearLayout mondayRight;
    LinearLayout tuesdayLeft;
    LinearLayout tuesdayRight;
    LinearLayout wednesdayLeft;
    LinearLayout wednesdayRight;
    LinearLayout thursdayLeft;
    LinearLayout thursdayRight;
    LinearLayout fridayLeft;
    LinearLayout fridayRight;
    LinearLayout saturdayLeft;
    LinearLayout saturdayRight;
    LinearLayout sundayLeft;
    LinearLayout sundayRight;
    TextView addAvailMH1;
    TextView addAvailMM1;
    TextView addAvailMT1;
    TextView addAvailMH2;
    TextView addAvailMM2;
    TextView addAvailMT2;

    TextView addAvailTH1;
    TextView addAvailTM1;
    TextView addAvailTT1;
    TextView addAvailTH2;
    TextView addAvailTM2;
    TextView addAvailTT2;

    TextView addAvailWH1;
    TextView addAvailWM1;
    TextView addAvailWT1;
    TextView addAvailWH2;
    TextView addAvailWM2;
    TextView addAvailWT2;

    TextView addAvailThH1;
    TextView addAvailThM1;
    TextView addAvailThT1;
    TextView addAvailThH2;
    TextView addAvailThM2;
    TextView addAvailThT2;

    TextView addAvailFH1;
    TextView addAvailFM1;
    TextView addAvailFT1;
    TextView addAvailFH2;
    TextView addAvailFM2;
    TextView addAvailFT2;

    TextView addAvailSH1;
    TextView addAvailSM1;
    TextView addAvailST1;
    TextView addAvailSH2;
    TextView addAvailSM2;
    TextView addAvailST2;

    TextView addAvailSuH1;
    TextView addAvailSuM1;
    TextView addAvailSuT1;
    TextView addAvailSuH2;
    TextView addAvailSuM2;
    TextView addAvailSuT2;


    ScrollChoice scrollH;
    ScrollChoice scrollM;
    ScrollChoice scrollT;
    ArrayList<String> hours = new ArrayList<>();
    ArrayList<String> minutes = new ArrayList<>();
    ArrayList<String> timeOfDay = new ArrayList<>();
    String choiceH;

    Button submitAvail;
    DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeOfDay.add("AM");
        timeOfDay.add("PM");

        for (int i=1;i<13;i++) {
            if (i<10) {
                hours.add("0"+String.valueOf(i));
            }else{
                hours.add(String.valueOf(i));
            }
        }
        for (int i=0;i<60;i++) {
            if (i == 0) {
                minutes.add("00");
            }else if(i<10){
                minutes.add("0"+String.valueOf(i));
            }else{
                minutes.add(String.valueOf(i));
            }
        }


        setContentView(R.layout.activity_add_availabilities);

        scrollH = findViewById(R.id.scrollChoiceH);
        scrollH.addItems(hours,0);
        scrollM = findViewById(R.id.scrollChoiceM);
        scrollM.addItems(minutes,0);
        scrollT = findViewById(R.id.scrollChoiceT);
        scrollT.addItems(timeOfDay,0);

        scrollH.setVisibility(View.GONE);
        scrollM.setVisibility(View.GONE);
        scrollT.setVisibility(View.GONE);

         mondayLeft = findViewById(R.id.MondayChoice1);
         mondayRight = findViewById(R.id.MondayChoice2);
         tuesdayLeft = findViewById(R.id.TuesdayChoice1);
         tuesdayRight = findViewById(R.id.TuesdayChoice2);
         wednesdayLeft = findViewById(R.id.WednesdayChoice1);
         wednesdayRight = findViewById(R.id.WednesdayChoice2);
         thursdayLeft = findViewById(R.id.ThursdayChoice1);
        thursdayRight = findViewById(R.id.ThursdayChoice2);
         fridayLeft = findViewById(R.id.FridayChoice1);
         fridayRight = findViewById(R.id.FridayChoice2);
         saturdayLeft = findViewById(R.id.SaturdayChoice1);
         saturdayRight = findViewById(R.id.SaturdayChoice2);
         sundayLeft = findViewById(R.id.SundayChoice1);
         sundayRight = findViewById(R.id.SundayChoice2);

        addAvailMH1 = findViewById(R.id.MondayH1);
        addAvailMM1 = findViewById(R.id.MondayM1);
        addAvailMT1 = findViewById(R.id.MondayT1);
        addAvailMH2 = findViewById(R.id.MondayH2);
        addAvailMM2 = findViewById(R.id.MondayM2);
        addAvailMT2 = findViewById(R.id.MondayT2);

        addAvailTH1 = findViewById(R.id.TuesdayH1);
        addAvailTM1 = findViewById(R.id.TuesdayM1);
        addAvailTT1 = findViewById(R.id.TuesdayT1);
        addAvailTH2 = findViewById(R.id.TuesdayH2);
        addAvailTM2 = findViewById(R.id.TuesdayM2);
        addAvailTT2 = findViewById(R.id.TuesdayT2);

        addAvailWH1 = findViewById(R.id.WednesdayH1);
        addAvailWM1 = findViewById(R.id.WednesdayM1);
        addAvailWT1 = findViewById(R.id.WednesdayT1);
        addAvailWH2 = findViewById(R.id.WednesdayH2);
        addAvailWM2 = findViewById(R.id.WednesdayM2);
        addAvailWT2 = findViewById(R.id.WednesdayT2);

        addAvailThH1 = findViewById(R.id.ThursdayH1);
        addAvailThM1 = findViewById(R.id.ThursdayM1);
        addAvailThT1 = findViewById(R.id.ThursdayT1);
        addAvailThH2 = findViewById(R.id.ThursdayH2);
        addAvailThM2 = findViewById(R.id.ThursdayM2);
        addAvailThT2 = findViewById(R.id.ThursdayT2);

        addAvailFH1 = findViewById(R.id.FridayH1);
        addAvailFM1 = findViewById(R.id.FridayM1);
        addAvailFT1 = findViewById(R.id.FridayT1);
        addAvailFH2 = findViewById(R.id.FridayH2);
        addAvailFM2 = findViewById(R.id.FridayM2);
        addAvailFT2 = findViewById(R.id.FridayT2);

        addAvailSH1 = findViewById(R.id.SaturdayH1);
        addAvailSM1 = findViewById(R.id.SaturdayM1);
        addAvailST1 = findViewById(R.id.SaturdayT1);
        addAvailSH2 = findViewById(R.id.SaturdayH2);
        addAvailSM2 = findViewById(R.id.SaturdayM2);
        addAvailST2 = findViewById(R.id.SaturdayT2);

        addAvailSuH1 = findViewById(R.id.SundayH1);
        addAvailSuM1 = findViewById(R.id.SundayM1);
        addAvailSuT1 = findViewById(R.id.SundayT1);
        addAvailSuH2 = findViewById(R.id.SundayH2);
        addAvailSuM2 = findViewById(R.id.SundayM2);
        addAvailSuT2 = findViewById(R.id.SundayT2);

        submitAvail = findViewById(R.id.submitAvail);

        selectBox(mondayLeft,addAvailMH1,addAvailMM1,addAvailMT1);
        selectBox(mondayRight,addAvailMH2,addAvailMM2,addAvailMT2);

        selectBox(tuesdayLeft,addAvailTH1,addAvailTM1,addAvailTT1);
        selectBox(tuesdayRight,addAvailTH2,addAvailTM2,addAvailTT2);

        selectBox(wednesdayLeft,addAvailWH1,addAvailWM1,addAvailWT1);
        selectBox(wednesdayRight,addAvailWH2,addAvailWM2,addAvailWT2);

        selectBox(thursdayLeft,addAvailThH1,addAvailThM1,addAvailThT1);
        selectBox(thursdayRight,addAvailThH2,addAvailThM2,addAvailThT2);

        selectBox(fridayLeft,addAvailFH1,addAvailFM1,addAvailFT1);
        selectBox(fridayRight,addAvailFH2,addAvailFM2,addAvailFT2);

        selectBox(saturdayLeft,addAvailSH1,addAvailSM1,addAvailST1);
        selectBox(saturdayRight,addAvailSH2,addAvailSM2,addAvailST2);

        selectBox(sundayLeft,addAvailSuH1,addAvailSuM1,addAvailSuT1);
        selectBox(sundayRight,addAvailSuH2,addAvailSuM2,addAvailSuT2);

        submitAvail = findViewById(R.id.submitAvail);




    }


    private void selectBox(final LinearLayout layout,final TextView hours, final TextView minutes, final TextView timeOfDay){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime(hours,minutes,timeOfDay);
            }
        });
    }
    private void selectTime(final TextView hour, final TextView minute, final TextView currTimeOfDay){
        scrollH = findViewById(R.id.scrollChoiceH);
        scrollH.addItems(hours,0);
        scrollM = findViewById(R.id.scrollChoiceM);
        scrollM.addItems(minutes,0);
        scrollT = findViewById(R.id.scrollChoiceT);
        scrollT.addItems(timeOfDay,0);

        scrollH.setVisibility(View.VISIBLE);
        scrollM.setVisibility(View.VISIBLE);
        scrollT.setVisibility(View.VISIBLE);
        scrollH.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                hour.setText(name);
            }
        });
        scrollM.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                minute.setText(name);
            }
        });
        scrollT.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                currTimeOfDay.setText(name);
            }
        });
        submitAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scrollH.getVisibility()== View.VISIBLE){
                    scrollH.setVisibility(View.GONE);
                    scrollM.setVisibility(View.GONE);
                    scrollT.setVisibility(View.GONE);
                }else{
                    databaseServices = FirebaseDatabase.getInstance().getReference("users");
                    //Availability avail = new Availability(getIntent().getStringExtra("item"),addAvailMH1.getText().toString().trim(),addAvailT.getText().toString().trim(),addAvailW.getText().toString().trim(),addAvailThurs.getText().toString().trim(),addAvailF.getText().toString().trim(),addAvailS.getText().toString().trim(),addAvailSun.getText().toString().trim());
                    //databaseServices.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities").child(avail.getServiceTitle()).setValue(avail);
                    startActivity(new Intent(AddAvailabilities.this, ServiceProvider.class));
                    Toast.makeText(AddAvailabilities.this, "Uploading To DataBase Coming Soon..", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
