package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




public class CustomizeUserHome extends AppCompatActivity {

    DatabaseReference db;
    Switch swSendMessages;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customiseuserhome);

        swSendMessages = findViewById(R.id.swSendMessages);
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("User Settings");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                    String key = childsnap.getKey();
                    String value = (String) childsnap.getValue();

                    if (key.equals("Send Message")) {
                        if (value.equals("yes")) {
                            swSendMessages.setChecked(true);
                        } else {
                            swSendMessages.setChecked(false);
                        }
                    }
                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        swSendMessages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleMessages();
            }
        });

    }

    private void toggleMessages() {
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("User Settings").child("Send Message");
        if (swSendMessages.isChecked()) {
            db.setValue("yes");
        } else {
            db.setValue("no");
        }
    }
}
