package com.steam.appseg2105;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
public class SignUp extends AppCompatActivity {

    //NEED TO ADD SECOND PASSWORD AND VERIFY THEY ARE SAME
    DatabaseReference databaseUsers;
    EditText usernameEdit;
    EditText passwordEdit;
    Button signUpButton;
    EditText emailEdit;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailEdit = findViewById(R.id.emailEdit);
        usernameEdit = findViewById(R.id.usernameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        signUpButton = findViewById(R.id.signupButton);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }
    private void addUser() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        FirebaseAuth.getInstance().signOut();
        //wont create an account with dupe email
        //creates account and logs in after delay (the while loop waits for this delay)
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Magic here
                                }
                            }, 1000);
                            addToUserModel();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUp.this, "User with this email already exists.", Toast.LENGTH_SHORT).show();
                                usernameEdit.setText("");
                                passwordEdit.setText("");
                                emailEdit.setText("");
                                recreate();
                            }
                        }


                        // ...
                    }
                });
        //delay to give time to create account and login on firebase


    }
    private void addToUserModel(){
        String username = usernameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        FirebaseUser userNew = FirebaseAuth.getInstance().getCurrentUser();
        if(userNew != null){
            //creates the user with the id of the email account
            if (!(TextUtils.isEmpty(username) && TextUtils.isEmpty(password))) {
                String id = userNew.getUid();
                User userAccount = new User(id, username, password, "Admin");
                databaseUsers.child(id).setValue(userAccount);
                usernameEdit.setText("");
                passwordEdit.setText("");
                emailEdit.setText("");
                Toast.makeText(this, "Account Creation Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUp.this, SignIn.class));
            } else {
                Toast.makeText(this, "Account Creation Unsuccessful", Toast.LENGTH_LONG).show();
            }

        }
    }

}



