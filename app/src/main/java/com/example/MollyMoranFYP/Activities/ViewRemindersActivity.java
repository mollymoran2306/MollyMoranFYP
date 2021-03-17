package com.example.MollyMoranFYP.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.ReminderAdapter;
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

public class ViewRemindersActivity extends AppCompatActivity {

    //Widgets
    RecyclerView recyclerView;

    private DatabaseReference myRef, db;
    private ArrayList<Reminder> reminderList;
    private ReminderAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreminders);
        setTitle("Reminders");
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setHasFixedSize(true);
        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();

        reminderList = new ArrayList<>();
        mAdapter = new ReminderAdapter(this, reminderList);
        recyclerView.setAdapter(mAdapter);

        if (recyclerView.getChildCount() == 0)
        {
            Toast.makeText(ViewRemindersActivity.this, "No Reminders to show!", Toast.LENGTH_SHORT).show();
        }


        /*	Code	below	is	based	on	MyDay - master opensource reminders application
                by Edge555 url:https://github.com/edge555/MyDay
                     */
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
            //END

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            //what happens if the action is cancelled
            }
        });

          /*
        This block of code is adapted from MyRecyclerViewApp by Michael Gleeson
         */
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {

            }

            @Override
            public void onRightSwipe(View view, int position) {
            }

            @Override
            public void onClick(View view, int position) {


            }

            public void onLongClick(View view, int position){
                showActionsDialog(position);
            }
        }
        ) );
    }
    //END

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete", "Cancel"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    deleteNote(position);
                } else {
                    dialog.cancel();
                }
            }
        });
        builder.show();
    }


    private void deleteNote(int position) {
       removeitem(position);
    }

    /*	Code	below	is	based	on	MyDay - master opensource reminders application
                by Edge555 url:https://github.com/edge555/MyDay
                     */
    public void removeitem(int position) {
        Reminder curreminder = reminderList.get(position);
        String delid = curreminder.getFull();
        FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = curuser.getUid();
        db = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(uid)
                .child("Reminder")
                .child(delid);
        db.setValue(null);
        reminderList.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Reminder Deleted", Toast.LENGTH_LONG).show();
    }
    //END


}
