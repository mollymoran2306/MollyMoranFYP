package com.example.MollyMoranFYP.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.UsersAdapter;
import com.example.MollyMoranFYP.Models.User;
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


public class CustomizeUserHome extends AppCompatActivity {

    DatabaseReference db2;
    Switch swSendMessages;
    RecyclerView recyclerView;

    private DatabaseReference myRef, db;
    private ArrayList<User> userList;
    private UsersAdapter mAdapter;
    private String i;
    private ImageView userImage;
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

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setHasFixedSize(true);
        userImage = findViewById(R.id.userImage);
        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
       // String uid = cursor.getUid();

        userList = new ArrayList<>();
        mAdapter = new UsersAdapter(this, userList);
        recyclerView.setAdapter(mAdapter);

        /*
        This block of code is adapted from MyRecyclerViewApp by Michael Gleeson
         */
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                //code as per your need
              //  Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightSwipe(View view, int position) {
                //code as per your need
               // Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
                // Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                showActionsDialog(position);
            }

            public void onLongClick(View view, int position){
                //code as per your need
              //  Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
            }
        }
        ) );



        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Usernames");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("Profile Pic").exists()) {
                        User r = new User(
                                ds.child("Name").getValue(String.class),
                                ds.child("User Type").getValue(String.class),
                                ds.child("ID").getValue(String.class),
                                ds.child("Profile Pic").getValue(String.class)
                        );
                        userList.add(r); }
                    else {
                        User r = new User(
                                ds.child("Name").getValue().toString(),
                                ds.child("User Type").getValue(String.class),
                                ds.child("ID").getValue(String.class)
                        );
                        userList.add(r);
                    }

                    //crashes if theres no image, add error handling here

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //what happens if the action is cancelled
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


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete User", "Cancel"};

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
        User curmessage = userList.get(position);
        String delid = curmessage.getUserID();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db2 = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Users")
                .child(uid)
                .child("Usernames")
                .child(delid);
        db2.setValue(null);
        userList.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
//        finish();
//        startActivity(getIntent());
    }
    //END
}
