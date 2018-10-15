package com.steam.appseg2105;

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

public class SignIn extends AppCompatActivity {
    TextView logInText;
    EditText emailEdit;
    EditText passwordEdit;
    Button logInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        logInText = findViewById(R.id.LoginText);
        emailEdit = findViewById(R.id.emailEditIn);
        passwordEdit = findViewById(R.id.passwordEditIn);
        logInButton = findViewById(R.id.LogInButton);
        logInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signUserIn();
            }
        });
    }
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
                        } else {
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
        }


