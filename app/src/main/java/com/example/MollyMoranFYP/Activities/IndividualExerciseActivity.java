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

        if (exercise.equals("Sideways Walking")) {
            setContentView(R.layout.exercise_sidewayswalking);
        }
        else if (exercise.equals("Simple Grapevine")) {
            setContentView(R.layout.exercise_simplegrapevine);
        }
//        else {
//            setContentView(R.layout.exercise_sidewayswalking);
//        }
    }
}
