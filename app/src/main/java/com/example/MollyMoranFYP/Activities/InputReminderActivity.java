package com.example.MollyMoranFYP.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class InputReminderActivity extends AppCompatActivity {
    private Button adderset;
    private Button btnBack;
    private EditText addername, adderdes;
    private String curdate = "", curtime = "", taskdate = "", tasktime = "";
    private TextView repeattv, addertimetv, adderdatetv;
    private ImageView addermarker;
    private DatabaseReference db;
    private static int noww, repeat, color = 0;
    private static final int REQUEST_CODE_SPEECH = 1000;
    private static final int PICK_CONTACT_CALL = 1, PICK_CONTACT_MSG = 2;
    String[] rep = new String[]{"None", "Daily", "Weekly", "Monthly", "Yearly"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputreminder);
        repeat = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noww = extras.getInt("now");
        }
        adderset = findViewById(R.id.adderset);
        adderset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settask();
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputReminderActivity.this, AdminHomeActivity.class);

                startActivity(intent);
            }
        });
    }

    public void setdate(View view) {
        if (noww == 2) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        String y = Integer.toString(year);
        String m = Integer.toString(month);
        String d = Integer.toString(date);
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
                taskdate = y + m + d;
                adderdatetv = findViewById(R.id.adderdatetv);
                adderdatetv.setText(parsedate(taskdate));
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    public void settime(View view) {
        if (noww == 2) {
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
                tasktime = s;
                addertimetv = findViewById(R.id.addertimetv);
                addertimetv.setText(parsetime(tasktime));
            }
        }, hour, min, true);
        timePickerDialog.show();
    }

    public void settask() {
        boolean flag = true;
        if (taskdate.compareTo(curdate) < 0 && noww != 2) {
            flag = false;
        } else if (taskdate.compareTo(curdate) == 0 && noww != 2) {
            if (tasktime.compareTo(curtime) < 0) {
                flag = false;
            }
        }
        Log.d("chk", String.valueOf(noww));
        addername = findViewById(R.id.addername);
        String task = addername.getText().toString();
        adderdes = findViewById(R.id.adderdes);
        String details = adderdes.getText().toString();
        String ct = curdate + curtime;
        String tt = taskdate + tasktime;
        if (task.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Task name is empty", Toast.LENGTH_LONG).show();
            flag = false;
        }
        if (noww != 2) {
            if (taskdate.isEmpty() || tasktime.isEmpty()) {
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
            String fin = taskdate + tasktime + Integer.toString(rNum);
            FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
            if (curuser != null) {
                String uid = curuser.getUid();
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
                if (noww == 2) {
                    reminder = new Reminder(task, details, "---", "---", "None", fin);
                } else {
                    reminder = new Reminder(task, details, taskdate, tasktime, rep[repeat], fin);
                }
                val.put(fin, reminder);
                db.updateChildren(val);
            }
            Toast.makeText(getApplicationContext(), "Reminder Added!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(InputReminderActivity.this, InputReminderActivity.class);
            startActivity(intent);
        }
    }

    public void setrepeat(View view) {
        if (noww == 2) {
            return;
        }
        repeat = (repeat + 1) % 5;
        repeattv = findViewById(R.id.adderrepeat);
        repeattv.setText(rep[repeat]);
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



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case REQUEST_CODE_SPEECH: {
//                if (data != null) {
//                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    addername = findViewById(R.id.addername);
//                    String res = result.get(0);
//                    res = res.substring(0, 1).toUpperCase() + res.substring(1);
//                    addername.setText(res);
//                }
//                break;
//            }
//            case PICK_CONTACT_CALL: {
//                Uri contactData = data.getData();
//                Cursor c = getContentResolver().query(contactData, null, null, null, null);
//                if (c.moveToFirst()) {
//                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//                    addername = findViewById(R.id.addername);
//                    addername.setText("Call " + name);
//                }
//                break;
//            }
//            case PICK_CONTACT_MSG: {
//                Uri contactData = data.getData();
//                Cursor c = getContentResolver().query(contactData, null, null, null, null);
//                if (c.moveToFirst()) {
//                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
//                    addername = findViewById(R.id.addername);
//                    addername.setText("Send SMS to " + name);
//                }
//                break;
//            }
//        }
//    }
}