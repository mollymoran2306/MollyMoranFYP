package com.example.MollyMoranFYP.Activities;

import android.content.DialogInterface;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    private DatabaseReference myRef, db;
    private ArrayList<User> userList;
    private UsersAdapter mAdapter;
    private String i;
    private ImageView userImage;

    private static final String TAG = "*ManageUsersActivity*";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setHasFixedSize(true);
        userImage = findViewById(R.id.userImage);
        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();

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
                showActionsDialog(position);
            }

            public void onLongClick(View view, int position){
                //code as per your need
                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
            }
        }
        ) );



        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Usernames");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
        User curmessage = userList.get(position);
        String delid = curmessage.getUserID();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Usernames").child(delid);
        db.setValue(null);
        userList.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
    }
    //END



}

