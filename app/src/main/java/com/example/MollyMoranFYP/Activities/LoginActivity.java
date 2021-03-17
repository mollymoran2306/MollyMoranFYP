package com.example.MollyMoranFYP.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.MollyMoranFYP.R.layout.activity_login;


public class LoginActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button login;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    public static final String Type = "userType";
    private static final String TAG = "*LoginActivity*";


    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        getSupportActionBar().hide();


        sharedpreferences = getSharedPreferences(mypreference,MODE_PRIVATE);
        Log.d(TAG, "user type is " + sharedpreferences.getString(Type, "") );

        if(sharedpreferences.getBoolean("logged",false)){
            if (sharedpreferences.getString(Type, "").equals("Family Member/Carer")) {
                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity2.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        }

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.btnSendFeedback);

        // take this out!
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtPassword = findViewById(R.id.txtPassword);

        txtEmail.setText("molly.moran@gmail.com");
        txtPassword.setText("secret");
        //take this out!

        auth = FirebaseAuth.getInstance();

        TextView txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString() != null && password.getText().toString() != null) {
                    String txt_email = email.getText().toString();
                    String txt_password = password.getText().toString();
                    loginUser(txt_email, txt_password);
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter an Email and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*this method is adapted from the YouTube video Firebase Android Tutorial 3 - Firebase Login Authentication and Sign Out
     by ProgrammingKnowledge video can be found at https://www.youtube.com/watch?v=ItqsK5uhLBg, source code can be found at
     https://github.com/rishavk1102/FirebaseDemo
    */

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
               // sharedpreferences.edit().putBoolean("logged",true).apply();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("logged", true);
                editor.apply();
                startActivity(new Intent(LoginActivity.this, UserSetupActivity.class));
                finish();
            }

        });
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
