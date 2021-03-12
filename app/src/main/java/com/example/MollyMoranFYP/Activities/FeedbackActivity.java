package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class FeedbackActivity extends AppCompatActivity {
    Button btnSendFeedback;
    private DatabaseReference db;
    EditText feedback;
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    Random rand = new Random();
    int rNum = 100 + rand.nextInt((999 - 100) + 1);
    String fin =  Integer.toString(rNum);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedback = findViewById(R.id.txtFeedback);

        btnSendFeedback = findViewById(R.id.btnSendFeedback);
        btnSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fb = feedback.getText().toString();
                sendFeedback(fb);
                Toast.makeText(getApplicationContext(), "Feedback Sent!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(FeedbackActivity.this, AdminHomeActivity2.class);
                startActivity(intent);

            }
        });

    }

    private void sendFeedback(String feedback) {

        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Feedback").child(fin);
        HashMap<String, String> fb = new HashMap<>();
        fb.put("Feedback", feedback);
        fb.put("User ID", uid);
        fb.put("Date", date);
        db.setValue(fb);
    }
}
