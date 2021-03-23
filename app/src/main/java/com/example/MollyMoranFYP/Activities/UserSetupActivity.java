package com.example.MollyMoranFYP.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


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
import java.util.Map;
import java.util.TreeMap;
import java.util.Random;

import static com.example.MollyMoranFYP.R.layout.activity_usersetup;

public class UserSetupActivity extends AppCompatActivity {
    Spinner spUser, spUserType;
    DatabaseReference myRef, reff;
    Button btnLetsGo;
    EditText txtNewName;
    DatabaseReference db;
    SharedPreferences sharedpreferences;
    long maxid=1;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String ID = "userID";
    public static final String Type = "userType";
    public String id, uid;


    Random rand = new Random();
    int rNum = 100 + rand.nextInt((999 - 100) + 1);
    String fin =  Integer.toString(rNum);

    private static final String TAG = "*UserSetupActivity*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_usersetup);
        getSupportActionBar().hide();

        btnLetsGo = findViewById(R.id.btnLetsGo);
        txtNewName = findViewById(R.id.txtNewName);
        spUserType = findViewById(R.id.spUserType);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
         uid = cursor.getUid();



        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Usernames");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> usernames = new ArrayList<String>();

                for (DataSnapshot usernameSnapshot: snapshot.getChildren()) {
                    String username = usernameSnapshot.child("Name").getValue(String.class);
                    usernames.add(username);
                }
                spUser = findViewById(R.id.spUser);
                ArrayAdapter<String> usernameAdapter = new ArrayAdapter<String>(UserSetupActivity.this, android.R.layout.simple_spinner_item, usernames);
                usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spUser.setAdapter(usernameAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                final List<String> IDs = new ArrayList<String>();
//
//                for (DataSnapshot usernameSnapshot: snapshot.getChildren()) {
//                    String id = usernameSnapshot.child("ID").getValue(String.class);
//                    IDs.add(id);
//                }
//                String ids = IDs.get()
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


        btnLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtNewName.getText().toString().trim().length() > 0) {
                    Log.d(TAG, "entered into if" );
                    saveUser();
                    String t = spUserType.getSelectedItem().toString();
                    String n = txtNewName.getText().toString();
                    Log.d(TAG, "string n is " + n);
                    //https://www.journaldev.com/9412/android-shared-preferences-example-tutorial
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, n);
                    editor.putString(Type, t);
                    editor.apply();
                    Log.d(TAG, "shared pref name is " + sharedpreferences.getString(Name, ""));
                    Log.d(TAG, "shared pref type is " + sharedpreferences.getString(Type, ""));


                    if (sharedpreferences.getString(Type, "").equals("Family Member/Carer")) {
                        Intent intent = new Intent(UserSetupActivity.this, CarerHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(UserSetupActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

//                    editor.apply();
                } else {
                    //shouldn't be entering into this!!

                    myRef.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final List<String> IDs = new ArrayList<String>();

                            for (DataSnapshot usernameSnapshot: snapshot.getChildren()) {
                                String id = usernameSnapshot.child("ID").getValue(String.class);
                                IDs.add(id);
                            }
                            Log.d(TAG, "entered into else" );
                            String id2 = IDs.get(spUser.getSelectedItemPosition());
                            Log.d(TAG, "id is " + id2);
                            String name = spUser.getSelectedItem().toString();
                            Log.d(TAG, "name is " + name);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Name, name);
                            editor.putString(ID, id2);

                            editor.apply();

                            reff= FirebaseDatabase
                                    .getInstance()
                                    .getReference()
                                    .child("Users")
                                    .child(uid)
                                    .child("Usernames")
                                    .child(id2);
                            Log.d(TAG, "reff is  " + reff);

                            reff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String type = dataSnapshot.child("User Type").getValue(String.class);
                                    Log.d(TAG, "type is " + type);

                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Type, type);
                                    editor.apply();

                                    Log.d(TAG, "shared pref type is " + sharedpreferences.getString(Type, ""));
                                    if (sharedpreferences.getString(Type, "").equals("Family Member/Carer")) {
                                        Intent intent = new Intent(UserSetupActivity.this, CarerHomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(UserSetupActivity.this, UserHomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                Log.d(TAG, "shared pref name is 2 " + sharedpreferences.getString(Name, ""));

            }
        });
    }

    public void saveUser() {

        if (!spUserType.getSelectedItem().toString().equals("Select User Type")) {

        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();

        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Usernames");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxid = snapshot.getChildrenCount();
                Log.d(TAG, "snapshot count is " + maxid );

                String name = txtNewName.getText().toString();
                String userType = spUserType.getSelectedItem().toString();

                Log.d(TAG, "userType is " + userType );

                String id = String.valueOf(maxid + 1);
                Map<String, Object> val = new TreeMap<>();
                val.put("ID", id);
                val.put("Name", name);
                val.put("User Type", userType);
                myRef.child(String.valueOf(maxid + 1)).updateChildren(val);
                // db.child(id).updateChildren(val);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(ID, id);
//                editor.putString(Type, userType);
               editor.apply();

                Log.d(TAG, "second shared pref type is " + sharedpreferences.getString(Type, ""));
                Log.d(TAG, "id in shared preferences is " + id );
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//            if (sharedpreferences.getString(Name, "").equals("Family Member/Carer")) {
//                Intent intent = new Intent(UserSetupActivity.this, AdminHomeActivity2.class);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(UserSetupActivity.this, UserHomeActivity.class);
//                startActivity(intent);
//            }
//
            } else {
            Toast.makeText(getApplicationContext(), "Please Select User Type!", Toast.LENGTH_LONG).show();

        }
     }
    }