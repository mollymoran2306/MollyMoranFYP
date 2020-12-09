package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

import static com.example.MollyMoranFYP.R.layout.activity_registration;

public class RegistrationActivity extends AppCompatActivity {

    //Some code here taken from MyDay - master opensource reminders application can be found at: https://github.com/edge555/MyDay


        private Button rb;
        String n, fn, sn, u, p;
        EditText firstName, lastName, email, password;
        FirebaseAuth mAuth;
        private DatabaseReference db;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(activity_registration);
            setTitle("Connect");

            // TAKE THIS OUT!!!
            TextView txtFirstName = findViewById(R.id.txtFirstName);
            TextView txtLastName = findViewById(R.id.txtLastName);
            TextView txtEmail = findViewById(R.id.txtEmail);
            TextView txtPassword = findViewById(R.id.txtPassword);

            txtFirstName.setText("Molly");
            txtLastName.setText("Moran");
            txtEmail.setText(String.format("moll@gmail.com", UUID.randomUUID().toString()));
            txtPassword.setText("secret");
            // TAKE THIS OUT!!!


            rb = findViewById(R.id.btnRegister);
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firstName = findViewById(R.id.txtFirstName);
                    lastName = findViewById(R.id.txtLastName);
                    email = findViewById(R.id.txtEmail);
                    password = findViewById(R.id.txtPassword);
                    fn = firstName.getText().toString();
                    sn = lastName.getText().toString();
                    n = fn + " " + sn;
                    u = email.getText().toString();
                    p = password.getText().toString();
                   // Checker chk = new Checker();
                    mAuth = FirebaseAuth.getInstance();
                    String s = "";
//                    if (!chk.name(n)) {
//                        if (!chk.name(fn)) {
//                            efn.setError("Enter a valid name");
//                            efn.requestFocus();
//                        } else if (!chk.name(sn)) {
//                            esn.setError("Enter a valid name");
//                            esn.requestFocus();
//                        }
//                    }
                    if (u.isEmpty()) {
                        email.setError("Enter an email address");
                        email.requestFocus();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(u).matches()) {
                        email.setError("Enter a valid email address");
                        email.requestFocus();
                    } else if (p.isEmpty()) {
                        password.setError("Enter a password");
                        password.requestFocus();
                    } else if (p.length() < 6) {
                        password.setError("Password must be minimum of 6 characters");
                        password.requestFocus();
                    }

                    //Complete Listener for error handling
                    else {
                        mAuth.createUserWithEmailAndPassword(u, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = cursor.getUid();
                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
                                    HashMap<String, String> info = new HashMap<>();
                                    info.put("Name", n);
                                    info.put("Email", u);
                                    db.setValue(info);
                                    TreeMap<String, Reminder> taskk = new TreeMap<String, Reminder>();
                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task");
                                    db.setValue(taskk);

                                    Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                                    finish();
                                    Intent intent = new Intent(RegistrationActivity.this, AdminHomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getApplicationContext(), "Email is already in use", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error : " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }


}
