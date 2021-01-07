package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.MessageAdapter;

import com.example.MollyMoranFYP.Models.Message;

import com.example.MollyMoranFYP.R;
import com.example.MollyMoranFYP.Utils.MyDividerItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ViewMessagesActivity extends AppCompatActivity {

    //Widgets
    RecyclerView recyclerView;

    private DatabaseReference myRef;
    private ArrayList<Message> messageList;
    private MessageAdapter mAdapter;
    private String i;

    private static final String TAG = "*ViewMessageActivity*";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Some code here taken from MyDay - master open source reminders application. Can be found at: https://github.com/edge555/MyDay
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmessages);
        setTitle("Message Board");

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setHasFixedSize(true);
        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();

        messageList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(mAdapter);

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message r = new Message(
                            ds.child("subject").getValue(String.class),
                            ds.child("messageText").getValue(String.class),
                            ds.child("image").getValue(String.class)
                    );
                    //crashes if theres no image, add error handling here
                    messageList.add(r);


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

