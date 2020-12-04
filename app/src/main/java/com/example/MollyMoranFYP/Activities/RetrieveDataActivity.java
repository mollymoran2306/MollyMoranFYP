package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetrieveDataActivity extends AppCompatActivity {
TextView a,b,c,d;
Button btn;
DatabaseReference reff;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataretrieve);

        a=findViewById(R.id.nameview);
        b=findViewById(R.id.ageview);
        c=findViewById(R.id.htView);
        d=findViewById(R.id.phview);
        btn=findViewById(R.id.btnload);

        btn.setOnClickListener(new View.OnClickListener() {

            // this code was take from the YouTube video "How to retrieve data
            // from Firebase in Android || Retrieve data from firebase || Android Firebase #4
            //by Educatree
            @Override
            public void onClick(View v) {
                reff= FirebaseDatabase.getInstance().getReference().child("Member").child("1");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String name= dataSnapshot.child("name").getValue().toString();
                        String age= dataSnapshot.child("age").getValue().toString();
                        String ht= dataSnapshot.child("ht").getValue().toString();
                        String ph = dataSnapshot.child("ph").getValue().toString();
                        a.setText(name);
                        b.setText(age);
                        c.setText(ht);
                        d.setText(ph);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

}
