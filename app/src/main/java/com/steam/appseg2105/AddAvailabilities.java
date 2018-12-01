package com.steam.appseg2105;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
// ADDING AND EDITING AVAILABILITIES ARE THE SAME AS IF THE AVAILABILITY ALREADY EXISTS IT WILL OVERWRITE IT
// MAY NEED TO CLICK MORE THAN ONCE
public class AddAvailabilities extends AppCompatActivity {

    private Spinner spinner;
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
        setContentView(R.layout.activity_add_availabilities);
        spinner = findViewById(R.id.spinnerAddy); //retrieve the spinner from the xml
        Set<DayOfWeek> daysOfWeek = EnumSet.noneOf( DayOfWeek.class );
        daysOfWeek.add(DayOfWeek.MONDAY);
        YearMonth ym = YearMonth.of(2018 , Calendar.getInstance().get(Calendar.MONTH)+1);
        LocalDate firstOfMonth = ym.atDay( 1 );
        final List<String> mondays = new ArrayList<>(5);
        LocalDate ld = firstOfMonth.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY));
            do {
                mondays.add(ld.toString());
                // Set up next loop.
                if((Calendar.getInstance().get(Calendar.DATE)+1)>Integer.parseInt(ld.toString().substring(ld.toString().length()-2,ld.toString().length())) && ym.toString().equals(YearMonth.of(2018,Calendar.getInstance().get(Calendar.MONTH)+1).toString()))
                {
                    mondays.remove(mondays.size()-1);
                }
                ld = ld.plusWeeks(1);
                if(!YearMonth.from(ld).equals(ym)){
                    ym = YearMonth.of(2018,Calendar.getInstance().get(Calendar.MONTH)+1);
                }

            } while (YearMonth.from(ld).equals(ym) && mondays.size() <5);


        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mondays);// Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        spinner.setAdapter(adapter);

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
                    String weekOf = String.valueOf(spinner.getSelectedItem());
                    if((addAvailMH1.getText().toString().equals(addAvailMH2.getText().toString()))||(addAvailTH1.getText().toString().equals(addAvailTH2.getText().toString()))||(addAvailWH1.getText().toString().equals(addAvailWH2.getText().toString()))||(addAvailThH1.getText().toString().equals(addAvailThH2.getText().toString()))||(addAvailFH1.getText().toString().equals(addAvailFH2.getText().toString()))||(addAvailSH1.getText().toString().equals(addAvailSH2.getText().toString()))||(addAvailSuH1.getText().toString().equals(addAvailSuH2.getText().toString()))){
                        Toast.makeText(AddAvailabilities.this, "One or more of the time slots was incorrect", Toast.LENGTH_LONG).show();
                    }else{
                        String monday = addAvailMH1.getText().toString() + ":" + addAvailMM1.getText().toString() +  addAvailMT1.getText().toString() + "-" + addAvailMH2.getText().toString() + ":" + addAvailMM2.getText().toString() + addAvailMT2.getText().toString();
                        String tuesday = addAvailTH1.getText().toString() + ":" + addAvailTM1.getText().toString() + addAvailTT1.getText().toString() + "-" + addAvailTH2.getText().toString() + ":" + addAvailTM2.getText().toString() + addAvailTT2.getText().toString();
                        String wednesday = addAvailWH1.getText().toString() + ":" + addAvailWM1.getText().toString() +  addAvailWT1.getText().toString() + "-" + addAvailWH2.getText().toString() + ":" + addAvailWM2.getText().toString() +  addAvailWT2.getText().toString();
                        String thursday = addAvailThH1.getText().toString() + ":" + addAvailThM1.getText().toString() + addAvailThT1.getText().toString() + "-" + addAvailThH2.getText().toString() + ":" + addAvailThM2.getText().toString() + addAvailThT2.getText().toString();
                        String friday = addAvailFH1.getText().toString() + ":" + addAvailFM1.getText().toString() + addAvailFT1.getText().toString() + "-" + addAvailFH2.getText().toString() + ":" + addAvailFM2.getText().toString() +  addAvailFT2.getText().toString();
                        String saturday = addAvailSH1.getText().toString() + ":" + addAvailSM1.getText().toString() + addAvailST1.getText().toString() + "-" + addAvailSH2.getText().toString() + ":" + addAvailSM2.getText().toString() +  addAvailST2.getText().toString();
                        String sunday = addAvailSuH1.getText().toString() + ":" + addAvailSuM1.getText().toString() + addAvailSuT1.getText().toString() + "-" + addAvailSuH2.getText().toString() + ":" + addAvailSuM2.getText().toString() +  addAvailSuT2.getText().toString();
                        databaseServices = FirebaseDatabase.getInstance().getReference("users");
                        Availability avail = new Availability(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                        databaseServices.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Availabilities").child(getIntent().getStringExtra("item")).child(weekOf).setValue(avail);
                        startActivity(new Intent(AddAvailabilities.this, ServiceProvider.class));
                        Toast.makeText(AddAvailabilities.this, "Successfully added availabilities ", Toast.LENGTH_LONG).show();
                    }
                    }

            }
        });

    }
    public String createLd(String choice, String ldOld){
        String ld;
        if(choice.equals("edit")){
            ld = ldOld+" (Already Exists)";
        }else{
            ld = ldOld;
        }
        return ld;
    }
}
