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


import com.example.MollyMoranFYP.Models.Message;
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

    Random rand = new Random();
    int rNum = 100 + rand.nextInt((999 - 100) + 1);
    String fin =  Integer.toString(rNum);

    private static final String TAG = "*RegistrationActivity*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_usersetup);
        btnLetsGo = findViewById(R.id.btnLetsGo);
        txtNewName = findViewById(R.id.txtNewName);
        spUserType = findViewById(R.id.spUserType);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        String uid = cursor.getUid();


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


        btnLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNewName.getText().toString().trim().length() > 0)  {
                    saveUser();
                    String n = txtNewName.getText().toString();
                    //https://www.journaldev.com/9412/android-shared-preferences-example-tutorial
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, n);

                    editor.commit();
                } else {
                    FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = cursor.getUid();
                    String id = String.valueOf(spUser.getSelectedItemPosition() + 1);
                    reff= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child(id);
                    Log.d(TAG, "reff is  " + reff);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = spUser.getSelectedItem().toString();
                            Log.d(TAG, "name is " + name);

                            String id = String.valueOf(spUser.getSelectedItemPosition() + 1);
                            Log.d(TAG, "id is " + id);


                            String type = dataSnapshot.child("User Type").getValue().toString();
                           Log.d(TAG, "type is " + type);

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(Name, name);
                            editor.putString(ID, id);
                            editor.putString(Type, type);
                            editor.apply();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if (sharedpreferences.getString(Type, "").equals("Family Member/Carer")) {
                        Intent intent = new Intent(UserSetupActivity.this, AdminHomeActivity2.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(UserSetupActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                    }
//
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//                    editor.putString(Name, name);
//                    editor.putString(ID, id);
//                    editor.putString(userType, type);
//
//                    editor.commit();
                }

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

                String id = String.valueOf(maxid + 1);
                Map<String, Object> val = new TreeMap<>();
                val.put("ID", id);
                val.put("Name", name);
                val.put("User Type", userType);
                myRef.child(String.valueOf(maxid + 1)).updateChildren(val);
                // db.child(id).updateChildren(val);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(ID, id);
                editor.putString(Type, userType);
                editor.commit();

                Log.d(TAG, "id in shared preferences is" + id );
                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            if (sharedpreferences.getString(Name, "").equals("Family Member/Carer")) {
                Intent intent = new Intent(UserSetupActivity.this, AdminHomeActivity2.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(UserSetupActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }

} else {
            Toast.makeText(getApplicationContext(), "Please Select User Type!", Toast.LENGTH_LONG).show();

        }
    }
    }