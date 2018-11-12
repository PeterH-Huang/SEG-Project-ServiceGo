package com.steam.appseg2105;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CompleteProfile extends AppCompatActivity {
    private Spinner spinner; //DROPDOWN MENU TO SPECIFY TYPE OF USER THAT WILL BE SIGNING UP
    private List<String> optionList;
    private EditText phone;
    private EditText address;
    private EditText companyName;
    private Button submitProfile;
    private TextView licensed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        optionList = new ArrayList<>();
        phone  = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.Address);
        companyName = findViewById(R.id.companyName);
        submitProfile = findViewById(R.id.completeProfileBut);
        licensed = findViewById(R.id.licensed);
        optionList.add("no");
        optionList.add("yes");
        spinner = findViewById(R.id.optionSpinner); //retrieve the spinner from the xml
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,optionList);// Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        spinner.setAdapter(adapter);
        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                userReference.child("phone number").setValue(phone.getText().toString().trim());
                userReference.child("address").setValue(address.getText().toString().trim());
                userReference.child("company name").setValue(companyName.getText().toString().trim());
                userReference.child("licensed?").setValue(String.valueOf(spinner.getSelectedItem()));
                userReference.child("profile completed?").setValue("yes");
                finish();
            }
        });
    }
}
