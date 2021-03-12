package com.example.MollyMoranFYP.Activities;

import android.content.DialogInterface;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.MessageAdapter;

import com.example.MollyMoranFYP.Models.Message;

import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.example.MollyMoranFYP.Utils.MyDividerItemDecoration;
import com.example.MollyMoranFYP.Utils.MyTouchListener;
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

    private DatabaseReference myRef, db;
    private ArrayList<Message> messageList;
    private MessageAdapter mAdapter;
    private String i;

    private static final String TAG = "*ViewMessageActivity*";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        /*
        This block of code is adapted from MyRecyclerViewApp by Michael Gleeson
         */
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                //code as per your need
                Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightSwipe(View view, int position) {
                //code as per your need
                Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {
               // Movie movie = movieList.get(position);
               // Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
              //  showActionsDialog(position);
            }

            public void onLongClick(View view, int position){
                //code as per your need
                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
            }
        }
        ) );



        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Message r = new Message(
                            ds.child("subject").getValue(String.class),
                            ds.child("messageText").getValue(String.class),
                            ds.child("image").getValue(String.class),
                            ds.child("sender").getValue(String.class),
                            ds.child("profilePic").getValue(String.class),
                            ds.child("full").getValue(String.class)
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
//END
private void showActionsDialog(final int position) {
    CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Choose option");
    builder.setItems(colors, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
                // showNoteDialog(true, notesList.get(position), position);
            } else {
               deleteMessage(position);
            }
        }
    });
    builder.show();
}

    private void deleteMessage(int position) {
        removeitem(position);
    }

    /*	Code	below	is	based	on	MyDay - master opensource reminders application
                by Edge555 url:https://github.com/edge555/MyDay
                     */
    public void removeitem(int position) {
        Message curmessage = messageList.get(position);
        String delid = curmessage.getFull();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Message").child(delid);
        db.setValue(null);
        messageList.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Message Deleted", Toast.LENGTH_LONG).show();
   }
    //END



}

