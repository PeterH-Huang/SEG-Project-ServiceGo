package com.steam.appseg2105;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {

    //NEED TO ADD SECOND PASSWORD AND VERIFY THEY ARE SAME
    private DatabaseReference databaseUsers;
    private EditText usernameEdit;//WHERE USER ENTERS PREFERRED USER HANDLE
    private EditText passwordEdit;//WHERE USER ENTERS PASSWORD
    private Button signUpButton; //REFERS TO THE SUBMIT BUTTON
    private EditText emailEdit; //WHERE USER ENTERS EMAIL ADDRESS
    private Spinner spinner; //DROPDOWN MENU TO SPECIFY TYPE OF USER THAT WILL BE SIGNING UP
    private List<String> accountsList; //list of strings for the drop down menu
    private FirebaseAuth mAuth; //reference to database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailEdit = findViewById(R.id.emailEdit);//retrieve the email from the xml.
        usernameEdit = findViewById(R.id.usernameEdit);//retrieve the username from the xml.
        passwordEdit = findViewById(R.id.passwordEdit);//retrieve the password from the xml.
        signUpButton = findViewById(R.id.signupButton);//retrieve the submit button from the xml.
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        //The following code creates a drop down (spinner) with the account types as options
        accountsList = new ArrayList<>();
        accountsList.add("Home Owner");
        accountsList.add("Admin");
        accountsList.add("Service Provider");
        spinner = findViewById(R.id.spinner); //retrieve the spinner from the xml
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,accountsList);// Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        spinner.setAdapter(adapter);// Apply the adapter to the spinner
        // checks to see if an admin already exists, if so, delete this option from the drop down
        // this occurs on lines 78-80 specifically
        FirebaseDatabase.getInstance().getReference().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //loop that cycles through users
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //reference to the userID of each user
                            String userID = snapshot.getKey();
                            DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID);
                            keyReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //removes admin from list if there is already an admin
                                    if(dataSnapshot.child("typeOfAccount").getValue(String.class)!= null){
                                    if (dataSnapshot.child("typeOfAccount").getValue(String.class).equals("Admin")) {
                                        accountsList.remove("Admin");
                                    }}

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        //updates the drop down with the new list (or same if admin not deleted)
        adapter =  new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,accountsList);
        spinner.setAdapter(adapter);

    }
    //Checks to see if account credentials are valid, if so, it creates the account by calling addAuthUser()
    private void addUser() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String username = usernameEdit.getText().toString().trim();
        //To make sure they are not signed in
        FirebaseAuth.getInstance().signOut();
        //wont create an account with dupe email
        //creates account and logs in after delay (the while loop waits for this delay)
        if ((TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username))) {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
        }else if (password.length() < 6){
            Toast.makeText(this, "Your password must be at least 6 characters long.", Toast.LENGTH_LONG).show();
        } else if (password.length() > 16) {
            Toast.makeText(this, "Your password must be no more than 16 characters long.", Toast.LENGTH_LONG).show();
        } else if (password.equals(password.toLowerCase())) {
            Toast.makeText(this, "Your password must contain at least one uppercase character.", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isDigitsOnly(password)) {
            Toast.makeText(this, "Your password must contain letters.", Toast.LENGTH_LONG).show();
        } else if (!email.contains("@")) {
            Toast.makeText(this, "Please enter a correct E-mail address3.", Toast.LENGTH_LONG).show();
        }else if(!(email.indexOf("@") == email.lastIndexOf("@"))) {
            Toast.makeText(this, "Please enter a correct E-mail address2.", Toast.LENGTH_LONG).show();
        }else if (!(email.endsWith(".com") || email.endsWith(".ca") || email.endsWith(".co.uk") || email.endsWith(".net") || email.endsWith(".au"))) {
            Toast.makeText(this, "Please enter a correct E-mail address1.", Toast.LENGTH_LONG).show();
        }else if (username.length() > 16) {
            Toast.makeText(this, "Your username must be no more than 16 characters long.", Toast.LENGTH_LONG).show();
        }else if (username.length() < 6) {
            Toast.makeText(this, "Your username must be longer than 6 characters.", Toast.LENGTH_LONG).show();
        }else {
            addAuthUser();

        }
    }
    //adds user to user model, this stores the type of account, username and password under the user id
    private void addToUserModel() {
        String username = usernameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String typeOfAcc = String.valueOf(spinner.getSelectedItem());
        FirebaseUser userNew = FirebaseAuth.getInstance().getCurrentUser();
        if (userNew != null) {
                String id = userNew.getUid();
                //creates the user in the user model
                User userAccount = new User(id, username, password, typeOfAcc);
                databaseUsers.child(id).setValue(userAccount);
                usernameEdit.setText("");
                passwordEdit.setText("");
                emailEdit.setText("");
                Toast.makeText(this, "Account Creation Successful.", Toast.LENGTH_LONG).show();
                //starts the sign in activity
                startActivity(new Intent(SignUp.this, SignIn.class));
                finish();
        }}
        //creates the user in firebase database storing email and password, this account is used to log in
        //then it calls addToUserModel() to add to a realtime database
        private void addAuthUser() {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //delay to allow for automatic sign in
                            if (task.isSuccessful()) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Magic here
                                    }
                                }, 1000);

                                addToUserModel();
                            } else {
                                //if there is already an account with this email
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SignUp.this, "User with this email already exists.", Toast.LENGTH_SHORT).show();
                                    usernameEdit.setText("");
                                    passwordEdit.setText("");
                                    emailEdit.setText("");
                                    //restarts the activity with blank fields
                                    recreate();
                                }
                            }


                            // ...
                        }
                    });
        }


    }







