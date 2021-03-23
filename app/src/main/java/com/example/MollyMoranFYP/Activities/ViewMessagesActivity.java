package com.example.MollyMoranFYP.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.MessageAdapter;

import com.example.MollyMoranFYP.Models.Message;

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
import java.util.Collections;
import java.util.HashMap;

public class ViewMessagesActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private DatabaseReference myRef, db, db2, db3;
    private ArrayList<Message> messageList;
    private MessageAdapter mAdapter;
    private String i;
    private TextView txtMessageBoard;

    private static final String TAG = "*ViewMessageActivity*";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmessages);
        setTitle("Message Board");
        getSupportActionBar().hide();

        txtMessageBoard = findViewById(R.id.txtMessageBoard);

        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
            String uid = cursor.getUid();
            db2 = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("Users")
                    .child(uid)
                    .child("User Settings");

            db2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String name = dataSnapshot.child("Message Board Name").getValue().toString();
                    txtMessageBoard.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });



        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setHasFixedSize(true);
        messageList = new ArrayList<>();
        mAdapter = new MessageAdapter(this, messageList);
        recyclerView.setAdapter(mAdapter);

        /*
        This block of code is adapted from MyRecyclerViewApp by Michael Gleeson
         */
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {

              //  Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightSwipe(View view, int position) {

              //  Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {
                final Message r = messageList.get(position);
                Intent intent= new Intent(ViewMessagesActivity.this, FullScreenImage.class);
                intent.putExtra("image_url", r.getImage());
                intent.putExtra("subject", r.getSubject());
                intent.putExtra("sender", r.getSender());
                intent.putExtra("message", r.getMessageText());
                intent.putExtra("profile pic", r.getProfilePic());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ViewMessagesActivity.this.startActivity(intent);

                Log.d(TAG, "image url is " + r.getImage());
            }

            public void onLongClick(View view, int position){
                //code as per your need
                showActionsDialog(position);

            }
        }
        ) );

        txtMessageBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionsDialog2();

            }
        });

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
                            ds.child("full").getValue(String.class),
                            ds.child("date").getValue(String.class),
                            ds.child("time").getValue(String.class)
                    );
                    //crashes if theres no image, add error handling here
                    messageList.add(r);
                }
                Collections.reverse(messageList);
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
    CharSequence colors[] = new CharSequence[]{"Delete Message", "Cancel"};

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Choose option");
    builder.setItems(colors, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == 0) {
                deleteMessage(position);
            } else {
                dialog.cancel();
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

 private boolean showActionsDialog2() {
     final EditText etName = new EditText(this);
     AlertDialog dialog = new AlertDialog.Builder(this)
             .setTitle("Rename Message Board")
             .setMessage("Please enter a new name")
             .setView(etName)
             .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     if (String.valueOf(etName.getText()) != null) {
                         String name = String.valueOf(etName.getText());
                         FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
                         if (cursor != null) {
                             String uid = cursor.getUid();
                             db3 = FirebaseDatabase
                                     .getInstance()
                                     .getReference()
                                     .child("Users")
                                     .child(uid)
                                     .child("User Settings");
                             HashMap<String, Object> msg = new HashMap<>();
                             msg.put("Message Board Name", name);
                             db3.updateChildren(msg);
                             txtMessageBoard.setText(name);
                             Toast.makeText(getApplicationContext(), "Name Changed!", Toast.LENGTH_LONG).show();
                         }
                     } else {
                         Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_LONG).show();
                     }


                 }
             })
             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             dialog.cancel();
         }
     })
             .create();
     dialog.show();
     return true;
 }

}

