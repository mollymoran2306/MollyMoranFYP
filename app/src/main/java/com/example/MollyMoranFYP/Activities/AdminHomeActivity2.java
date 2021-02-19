package com.example.MollyMoranFYP.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AdminHomeActivity2 extends AppCompatActivity {
    private LinearLayout linSendMessage, linViewMessages, linViewReminders, linReminders;
    private Switch viewSwitch;
    private CardView cardViewMessages;
    private TextView txtWelcome;
    private DatabaseReference myRef;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome2);
        setTitle("Admin Homepage");

        txtWelcome = findViewById(R.id.txtWelcome);
        linReminders = findViewById(R.id.linNews);
        linSendMessage = findViewById(R.id.linSendMessage);
        linViewMessages = findViewById(R.id.linViewMessages);
        linViewReminders = findViewById(R.id.linSettings);
        cardViewMessages = findViewById(R.id.cvViewMessages);
        viewSwitch = findViewById(R.id.viewSwitch);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        txtWelcome.setText("Welcome, " + sharedpreferences.getString(Name, ""));


        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();

//        String uid = cursor.getUid();
//        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
//        myRef.child("Name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               String name = snapshot.getValue().toString();
//                txtWelcome.setText("Welcome, " + name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        linReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, InputReminderActivity.class);

                startActivity(intent);
            }

        });

        cardViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, ViewMessagesActivity.class);

                startActivity(intent);
            }

        });

        linSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, SendMessageActivity.class);

                startActivity(intent);
            }

        });

        linViewReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, ViewRemindersActivity.class);

                startActivity(intent);
            }

        });

        viewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(AdminHomeActivity2.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
