package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHomeActivity extends AppCompatActivity {

    private LinearLayout linNews, linWeather, linViewMessages, linSendMessage, linExercise;
    private CardView cvSendMessage;
    private Switch viewSwitch2;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhomepage);
        setTitle("User Homepage");
        viewSwitch2 = findViewById(R.id.viewSwitch2);

        cvSendMessage = findViewById(R.id.cvSendMessage);


        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();


        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("User Settings");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                   String n = snapshot.child("Send Message").getValue().toString();
                   System.out.println(n);
                   if (n.equals("no")) {
                       cvSendMessage.setVisibility(View.GONE);
                   }
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //what happens if the action is cancelled
            }
        });

        linNews = findViewById(R.id.linNews);
        linNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, NewsActivity.class);
                startActivity(intent);
            }

    });
        linWeather = findViewById(R.id.linWeather);
        linWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, WeatherActivity.class);
                startActivity(intent);
            }

        });

        linViewMessages = findViewById(R.id.linViewMessages);
        linViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, ViewMessagesActivity.class);
                startActivity(intent);
            }

        });

        linExercise = findViewById(R.id.linExercise);
        linExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, ExercisesActivity.class);
                startActivity(intent);
            }

        });


        cvSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, SendMessageActivity.class);
                startActivity(intent);
            }

        });

        viewSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(UserHomeActivity.this, AdminHomeActivity2.class);
                startActivity(intent);
            }
        });
}}
