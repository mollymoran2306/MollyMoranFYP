package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.ReminderAdapter;
import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRemindersActivity extends AppCompatActivity {

    //Widgets
    RecyclerView recyclerView;

    private DatabaseReference myRef;
    private ArrayList<Reminder> reminderList;
    private ReminderAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Some code here taken from MyDay - master open source reminders application. Can be found at: https://github.com/edge555/MyDay
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreminders);

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();

        reminderList = new ArrayList<>();
        mAdapter = new ReminderAdapter(this, reminderList);
        recyclerView.setAdapter(mAdapter);

        //want to change this to only display future reminders not past reminders

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Reminder r = new Reminder(
                            ds.child("title").getValue(String.class),
                            ds.child("des").getValue(String.class),
                            ds.child("date").getValue(String.class),
                            ds.child("time").getValue(String.class),
                            ds.child("repeat").getValue(String.class),
                            ds.child("full").getValue(String.class)
                    );
                    reminderList.add(r);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //what happens if the action is cancelled
            }
        });
    }
}
