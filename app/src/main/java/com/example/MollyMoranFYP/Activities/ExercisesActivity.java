package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;

public class ExercisesActivity extends AppCompatActivity {
    private LinearLayout linSidewaysWalking, linSimpleGrapevine, linSitToStand, linBicepCurls, linNeckRotation, linSidewaysBend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        getSupportActionBar().hide();

        linSidewaysWalking = findViewById(R.id.linSidewaysWalking);
        linSimpleGrapevine = findViewById(R.id.linSimpleGrapevine);
        linSitToStand = findViewById(R.id.linSitToStand);
        linBicepCurls = findViewById(R.id.linBicepCurls);
        linNeckRotation = findViewById(R.id.linNeckRotation);
        linSidewaysBend = findViewById(R.id.linSidewaysBend);



        linSidewaysWalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ExercisesActivity.this, IndividualExerciseActivity.class);
                intent.putExtra("exercise", "Sideways Walking");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ExercisesActivity.this.startActivity(intent);
            }

        });

        linSimpleGrapevine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ExercisesActivity.this, IndividualExerciseActivity.class);
                intent.putExtra("exercise", "Simple Grapevine");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ExercisesActivity.this.startActivity(intent);

            }

        });

        linSitToStand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ExercisesActivity.this, IndividualExerciseActivity.class);
                intent.putExtra("exercise", "Sit to Stand");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ExercisesActivity.this.startActivity(intent);

            }

        });

        linBicepCurls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ExercisesActivity.this, IndividualExerciseActivity.class);
                intent.putExtra("exercise", "Bicep Curls");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ExercisesActivity.this.startActivity(intent);

            }

        });

        linNeckRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ExercisesActivity.this, IndividualExerciseActivity.class);
                intent.putExtra("exercise", "Neck Rotation");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ExercisesActivity.this.startActivity(intent);
            }

        });

        linSidewaysBend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ExercisesActivity.this, IndividualExerciseActivity.class);
                intent.putExtra("exercise", "Sideways Bend");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ExercisesActivity.this.startActivity(intent);

            }

        });

    }
}
