package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

import static com.example.MollyMoranFYP.R.layout.activity_login;


public class LoginActivity extends AppCompatActivity {



    private EditText email;
    private EditText password;
    private Button login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        login = findViewById(R.id.btnLogin);

        // take this out!
        TextView txtEmail = findViewById(R.id.txtEmail);
        TextView txtPassword = findViewById(R.id.txtPassword);

        txtEmail.setText("sophmoran@gmail.com");
        txtPassword.setText("secret");
        //take this out!

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_email, txt_password);
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
                startActivity(new Intent(LoginActivity.this, UserSetupActivity.class));
                finish();
            }
        });
    }
}
