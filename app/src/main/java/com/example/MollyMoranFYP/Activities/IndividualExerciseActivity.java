package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;

public class IndividualExerciseActivity extends AppCompatActivity {

    private static final String TAG = "*MessageAdapter*";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String exercise = getIntent().getStringExtra("exercise");
        Log.d(TAG, "exercise is  " + exercise);
        getSupportActionBar().hide();

        if (exercise.equals("Sideways Walking")) {
            setContentView(R.layout.exercise_sidewayswalking);
        }
        else if (exercise.equals("Simple Grapevine")) {
            setContentView(R.layout.exercise_simplegrapevine);
        }
        else if (exercise.equals("Sit to Stand")) {
            setContentView(R.layout.exercise_sit_to_stand);
        }
        else if (exercise.equals("Bicep Curls")) {
            setContentView(R.layout.exercise_bicep_curls);
        }
        else if (exercise.equals("Neck Rotation")) {
            setContentView(R.layout.exercise_neck_rotation);
        }
        else if (exercise.equals("Sideways Bend")) {
            setContentView(R.layout.exercise_sidewaysbend);
        }

    }
}
