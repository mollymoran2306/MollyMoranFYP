package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;


public class AdminHomeActivity extends AppCompatActivity {

        private Button btnInputReminder;
        private Button btnViewReminders;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adminhome);
            setTitle("Admin Home");

            btnInputReminder = findViewById(R.id.btnInputReminder);
            btnViewReminders = findViewById(R.id.btnViewReminders);
            btnInputReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminHomeActivity.this, InputReminderActivity.class);

                    startActivity(intent);
                }
            });

            btnViewReminders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AdminHomeActivity.this, ViewRemindersActivity.class);

                    startActivity(intent);
                }
            });




        }

}
