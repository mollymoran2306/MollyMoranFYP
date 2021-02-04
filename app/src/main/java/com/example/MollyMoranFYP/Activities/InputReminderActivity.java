package com.example.MollyMoranFYP.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class  InputReminderActivity extends AppCompatActivity {
    private Button btnSet;
    private EditText txtTitle, txtDescription;
    private String curdate = "", curtime = "", reminderDate = "", reminderTime = "";
    private TextView tvRepeat, tvTime, tvDate;
    private Toolbar toolbar;

    private DatabaseReference db;
    private static int now, repeat;

    private static final String TAG = "*InputReminderActivity*";

    String[] rep = new String[]{"None", "Daily", "Weekly", "Monthly", "Yearly"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputreminder);
        setTitle("Input Reminder");
        repeat = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            now = extras.getInt("now");
        }
        btnSet = findViewById(R.id.btnSend);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReminder();
            }
        });

        Log.d(TAG, "on create method running");

    }

   /*	Code	below	is	based	on	MyDay - master opensource reminders application
                by Edge555 url:https://github.com/edge555/MyDay
                     */
    public void setDate(View view) {

        if (now == 2) {
            return;
        }
        //using the calendar class to get month, date and year
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        String y = Integer.toString(year);
        String m = Integer.toString(month);
        String d = Integer.toString(date);
        Log.d(TAG, "date"+ d );
        if (m.length() != 2)
            y += "0";
        if (d.length() != 2)
            m += "0";
        curdate = y + m + d;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String y = Integer.toString(year);
                String m = Integer.toString(month);
                String d = Integer.toString(date);
                if (m.length() != 2)
                    y += "0";
                if (d.length() != 2)
                    m += "0";
                reminderDate = y + m + d;
                tvDate = findViewById(R.id.tvDate);
                tvDate.setText(parsedate(reminderDate));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    public void setTime(View view) {
        if (now == 2) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        String s = "";
        String h = Integer.toString(hour);
        String m = Integer.toString(min);
        if (h.length() != 2)
            s += "0";
        s += h;
        if (m.length() != 2)
            s += "0";
        s += m;
        curtime = s;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                String s = "";
                String h = Integer.toString(hour);
                String m = Integer.toString(min);
                if (h.length() != 2)
                    s += "0";
                s += h;
                if (m.length() != 2)
                    s += "0";
                s += m;
                reminderTime = s;
                tvTime = findViewById(R.id.tvTime);
                tvTime.setText(parsetime(reminderTime));
            }
        }, hour, min, true);
        timePickerDialog.show();
    }

    public void setReminder() {
        boolean flag = true;
        if (reminderDate.compareTo(curdate) < 0 && now != 2) {
            flag = false;
        } else if (reminderDate.compareTo(curdate) == 0 && now != 2) {
            if (reminderTime.compareTo(curtime) < 0) {
                flag = false;
            }
        }

        txtTitle = findViewById(R.id.txtSubject);
        String task = txtTitle.getText().toString();
        txtDescription = findViewById(R.id.txtMessage);
        String details = txtDescription.getText().toString();
        String ct = curdate + curtime;
        String tt = reminderDate + reminderTime;
        if (task.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Reminder name is empty", Toast.LENGTH_LONG).show();
            flag = false;
        }
        if (now != 2) {
            if (reminderDate.isEmpty() || reminderTime.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Choose Time and Date", Toast.LENGTH_LONG).show();
                flag = false;
            }
            if (ct.compareTo(tt) > 0) {
                Toast.makeText(getApplicationContext(), "You can't choose previous time and date", Toast.LENGTH_LONG).show();
                flag = false;
            }
        }
        if (flag) {
            Random rand = new Random();
            int rNum = 100 + rand.nextInt((999 - 100) + 1);
            String fin = reminderDate + reminderTime + Integer.toString(rNum);
            FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
            if (cursor != null) {
                String uid = cursor.getUid();
//                if (noww == 2) {
//                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder");
//                } else {
                    db = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("Users")
                            .child(uid)
                            .child("Reminder");
//                }
                Map<String, Object> val = new TreeMap<>();
                Reminder reminder;
                if (now == 2) {
                    reminder = new Reminder(task, details, "---", "---", "None", fin);
                } else {
                    reminder = new Reminder(task, details, reminderDate, reminderTime, rep[repeat], fin);
                }
                val.put(fin, reminder);
                db.updateChildren(val);
                Log.d(TAG, "Reminder added to database " + reminder);
            }
            Toast.makeText(getApplicationContext(), "Reminder Added!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(InputReminderActivity.this, AdminHomeActivity2.class);
            startActivity(intent);
        }
    }

    public void setrepeat(View view) {
        if (now == 2) {
            return;
        }
        repeat = (repeat + 1) % 5;
        tvRepeat = findViewById(R.id.tvRepeat);
        tvRepeat.setText(rep[repeat]);
    }



    public String parsedate(String d) {
        String year = d.substring(0, 4), month = d.substring(4, 6), day = d.substring(6, 8);
        return day + "-" + month + "-" + year;
    }

    public String parsetime(String d) {
        String h = d.substring(0, 2), m = d.substring(2, 4);
        int hr = Integer.parseInt(h);
        Boolean pm = false;
        if (hr >= 12) {
            pm = true;
            hr %= 12;
        }
        if (hr == 0)
            hr = 12;
        h = String.valueOf(hr);
        return h + ":" + m + (pm ? " PM" : " AM");
    }


//END

}