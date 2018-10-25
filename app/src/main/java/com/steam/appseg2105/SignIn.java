package com.steam.appseg2105;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    private TextView logInText;
    private EditText emailEdit; //USER ENTERS EMAIL ASSOCIATED WITH ACCOUNT
    private EditText passwordEdit; //USER ENTERS THEIR PASSWORD
    private Button logInButton; //REFERS TO THE BUTTON WITH SUBMIT AS LABEL
    private DatabaseReference ref; //reference to database
    private DatabaseReference accountTypeRef; //reference to the type of account of the current user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        logInText = findViewById(R.id.LoginText);
        emailEdit = findViewById(R.id.emailEditIn);//retrieve the email from the xml.
        passwordEdit = findViewById(R.id.passwordEditIn);//retrieve the password from the xml.
        logInButton = findViewById(R.id.LogInButton);
        ref = FirebaseDatabase.getInstance().getReference();
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signUserIn();
            }
        });
    }
    // starts the correct activity based on the type of account chosen in the dropdown menu
    // signs the user in using email and password
    private void signUserIn() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "Sign in Successful",
                                    Toast.LENGTH_SHORT).show();
                            accountTypeRef = ref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("typeOfAccount");
                            accountTypeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String type = dataSnapshot.getValue(String.class);
                                    if(type.equals("Admin")){
                                        startActivity(new Intent(SignIn.this, Admin.class));
                                    }else if(type.equals("Home Owner")){
                                        startActivity(new Intent(SignIn.this, HomeOwner.class));
                                    }else if(type.equals("Service Provider")){
                                        startActivity(new Intent(SignIn.this, ServiceProvider.class));
                                    }else{
                                        Toast.makeText(SignIn.this, "Error in Referencing Type of Account",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
        }


