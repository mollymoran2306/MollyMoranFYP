package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;

public class ExercisesActivity extends AppCompatActivity {
    private LinearLayout linSidewaysWalking, linSimpleGrapevine;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        linSidewaysWalking = findViewById(R.id.linSidewaysWalking);
        linSimpleGrapevine = findViewById(R.id.linViewMessages);

        linSidewaysWalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExercisesActivity.this, Exercise_SidewaysWalking.class);

                startActivity(intent);
            }

        });

        linSimpleGrapevine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExercisesActivity.this, Exercise_SimpleGrapevine.class);

                startActivity(intent);
            }

        });

    }
}
