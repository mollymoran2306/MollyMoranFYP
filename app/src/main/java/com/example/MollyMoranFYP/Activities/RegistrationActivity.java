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
        EditText efn, esn, eu, ep;
        CheckBox regpasschk;
        FirebaseAuth mAuth;
        private DatabaseReference db;
        boolean doubleBackToExitPressedOnce = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(activity_registration);

            /* MOLLY - TAKE THIS OUT!!! */
            TextView txtFirstName = findViewById(R.id.txtFirstName);
            TextView txtLastName = findViewById(R.id.txtLastName);
            TextView txtEmail = findViewById(R.id.txtEmail);
            TextView txtPassword = findViewById(R.id.txtPassword);

            txtFirstName.setText("Fergal");
            txtLastName.setText("Moran");
            txtEmail.setText(String.format("fergal-%s@fergl.ie", UUID.randomUUID().toString()));
            txtPassword.setText("secret");
            /* MOLLY - TAKE THIS OUT!!! */


            rb = findViewById(R.id.btnRegister);
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    efn = findViewById(R.id.txtFirstName);
                    esn = findViewById(R.id.txtLastName);
                    eu = findViewById(R.id.txtEmail);
                    ep = findViewById(R.id.txtPassword);
                    fn = efn.getText().toString();
                    sn = esn.getText().toString();
                    n = fn + " " + sn;
                    u = eu.getText().toString();
                    p = ep.getText().toString();
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
                        eu.setError("Enter an email address");
                        eu.requestFocus();
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(u).matches()) {
                        eu.setError("Enter a valid email address");
                        eu.requestFocus();
                    } else if (p.isEmpty()) {
                        ep.setError("Enter a password");
                        ep.requestFocus();
                    } else if (p.length() < 6) {
                        ep.setError("Password must be minimum of 6 characters");
                        ep.requestFocus();
                    }
//                    else if (!chk.pass(p)) {
//                        ep.setError("Enter a valid password");
//                        ep.requestFocus();
                   // }
                    //Complete Listener for error handling
                    else {
                        mAuth.createUserWithEmailAndPassword(u, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = curuser.getUid();
                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
                                    HashMap<String, String> info = new HashMap<>();
                                    info.put("Name", n);
                                    info.put("Email", u);
                                    db.setValue(info);
                                    TreeMap<String, Reminder> taskk = new TreeMap<String, Reminder>();
                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Task");
                                    db.setValue(taskk);
                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Settings");
                                    HashMap<String, String> settings = new HashMap<>();
                                    settings.put("Darkmode", "False");
                                    settings.put("Vibration", "True");
                                    settings.put("Sound", "True");
                                    settings.put("Fullscreen", "False");
                                    settings.put("Notification", "True");
                                    db.setValue(settings);
                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
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
