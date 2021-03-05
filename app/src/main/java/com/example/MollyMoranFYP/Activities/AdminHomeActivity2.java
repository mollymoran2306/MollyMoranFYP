package com.example.MollyMoranFYP.Activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.example.MollyMoranFYP.Utils.Process;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class AdminHomeActivity2 extends AppCompatActivity {
    private LinearLayout linSendMessage, linViewMessages, linViewReminders, linManageUsers, linInputReminders, linSendFeedBack;
    private Switch viewSwitch;
    private CardView cardViewMessages;
    private TextView txtWelcome;
    private DatabaseReference myRef, db, dbb;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    private final String Channel_ID = "My_Channel";
    private final int Notification_ID = 001;
    ArrayList<Reminder> mexamplelist, mreminderlist;
    private static final String TAG = "*AdminHomeActivity2*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome2);
        setTitle("Admin Homepage");

        txtWelcome = findViewById(R.id.txtWelcome);
        linManageUsers = findViewById(R.id.linManageUsers);
        linSendMessage = findViewById(R.id.linSendMessage);
        linViewMessages = findViewById(R.id.linViewMessages);
        linViewReminders = findViewById(R.id.linViewReminders);
        cardViewMessages = findViewById(R.id.cvViewMessages);
        linInputReminders = findViewById(R.id.linInputReminders);
        linSendFeedBack = findViewById(R.id.linSendFeedback);

        viewSwitch = findViewById(R.id.viewSwitch);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        txtWelcome.setText("Welcome, " + sharedpreferences.getString(Name, ""));


        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();

//        String uid = cursor.getUid();
//        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Info");
//        myRef.child("Name").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               String name = snapshot.getValue().toString();
//                txtWelcome.setText("Welcome, " + name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        refreshTask();
        linManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, CustomizeUserHome.class);

                startActivity(intent);
            }

        });

        linInputReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, InputReminderActivity.class);

                startActivity(intent);
            }

        });

        cardViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, ViewMessagesActivity.class);

                startActivity(intent);
            }

        });

        linSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, SendMessageActivity.class);

                startActivity(intent);
            }

        });

        linViewReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, ViewRemindersActivity.class);

                startActivity(intent);
            }

        });

        viewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(AdminHomeActivity2.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });

        linSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity2.this, FeedbackActivity.class);

                startActivity(intent);
            }

        });

    }
  /*	Code	below	is	based	on	MyDay - master opensource reminders application
                by Edge555 url:https://github.com/edge555/MyDay
                     */


    private void refreshTask() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final String s = getTimeMethod("dd-MMM-yy-hh-mm-ss a");
                        if (s.substring(16, 18).equals("00")) {
                            final String curtime = process(s);
                            mexamplelist = new ArrayList<>();
                            FirebaseUser curuser = FirebaseAuth.getInstance().getCurrentUser();
                            final String uid = curuser.getUid();
                            if (curuser != null) {
                                db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder");
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        List<String> arr = new ArrayList<String>();
                                        int k;
                                        for (k = 0; k < mexamplelist.size(); k++) {
                                            arr.add(mexamplelist.get(k).getFull());
                                        }
                                        k = 0;
                                        for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                                            String date = childsnap.getKey();
                                            HashMap<String, String> hmp;
                                            hmp = (HashMap<String, String>) childsnap.getValue();
                                            Reminder rem = new Reminder(hmp.get("title"), hmp.get("des"), hmp.get("date"), hmp.get("time"), "None", hmp.get("marker"));
                                            String tasktime = date.substring(0, 12);
                                            if (curtime.compareTo(tasktime) == 0) {
                                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                                                    showNotification1(rem);
                                                } else {
                                                    showNotification2(rem);
                                                }
                                            } else if (curtime.compareTo(tasktime) > 0) {
                                                String repeat = hmp.get("repeat");
                                                if (repeat.equals("None")) {
                                                    dbb = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Past Reminders");
                                                    Map<String, Object> val = new TreeMap<>();
                                                    Reminder info = new Reminder(hmp.get("title"), hmp.get("des"), hmp.get("date"), hmp.get("time"), "None", hmp.get("marker"));
                                                    val.put(date, info);
                                                    dbb.updateChildren(val);
                                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder").child(date);
                                                    db.setValue(null);
                                                } else {
                                                    ArrayList<Integer> dates;
                                                    Process p = new Process();
                                                    dates = p.getdatelist(hmp.get("date"));
                                                    int y = dates.get(0);
                                                    int m = dates.get(1) + 1;
                                                    int d = dates.get(2);
                                                    int x = p.dateToInt(y, m, d);
                                                    dates.clear();
                                                    String curdate = "";
                                                    if (repeat.equals("Daily")) {
                                                        x++;
                                                        dates = p.intToDate(x);
                                                        y = dates.get(0);
                                                        m = dates.get(1);
                                                        d = dates.get(2);
                                                    } else if (repeat.equals("Weekly")) {
                                                        x += 7;
                                                        dates = p.intToDate(x);
                                                        y = dates.get(0);
                                                        m = dates.get(1);
                                                        d = dates.get(2);
                                                    } else if (repeat.equals("Monthly")) {
                                                        if (m == 12) {
                                                            m = 1;
                                                            y++;
                                                        } else {
                                                            m++;
                                                        }
                                                    } else {
                                                        y++;
                                                    }
                                                    String ys = Integer.toString(y);
                                                    String ms = Integer.toString(m - 1);
                                                    String ds = Integer.toString(d);
                                                    if (ms.length() != 2)
                                                        ys += "0";
                                                    if (ds.length() != 2)
                                                        ms += "0";
                                                    curdate = ys + ms + ds;
                                                    Map<String, Object> val2 = new TreeMap<>();
                                                    String date2 = curdate + hmp.get("time") + date.substring(12, 15);
                                                    Reminder info2 = new Reminder(hmp.get("title"), hmp.get("des"), curdate, hmp.get("time"), repeat, hmp.get("marker"));
                                                    val2.put(date2, info2);
                                                    db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder").child(date);
                                                    db.setValue(null);
                                                    dbb = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Reminder");
                                                    dbb.updateChildren(val2);
                                                }
                                            }
                                            Boolean exist = arr.contains(date);
                                            if (exist == false) {
                                                k++;
                                                mexamplelist.add(new Reminder(hmp.get("title"), hmp.get("des"), hmp.get("date"), hmp.get("time"), hmp.get("repeat"), hmp.get("marker")));
                                               // mAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                           // buildrecylerview();
                        }
                    }

                    ;
                });
            }
        }, 0, 1000);
    }

    private void showNotification1(Reminder reminder) {
        Log.d(TAG, "showNotification1 running");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_ID);
        builder.setSmallIcon(R.drawable.reminders);
        String title = reminder.getTitle();
        String text = reminder.getDes();
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(Notification_ID, builder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification2(Reminder reminder) {
        Log.d(TAG, "showNotification2 running");
        String id = "main_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        CharSequence name = "Channel Name";
        String description = "Channel Description";
        String title = reminder.getTitle();
        String text = reminder.getDes();
        Log.d(TAG, "text = " + text);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
        notificationChannel.setDescription(description);
        notificationChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent resintent = new Intent(this, AdminHomeActivity2.class);
        PendingIntent respenindent = PendingIntent.getActivity(this, 1, resintent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
         builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(respenindent);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1000, builder.build());
    }

    private String getTimeMethod(String formate) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    private String process(String s) {
        String f = "20" + s.substring(7, 9);
        String month = s.substring(3, 6);
        if (month.equals("Jan")) {
            f += "00";
        } else if (month.equals("Feb")) {
            f += "01";
        } else if (month.equals("Mar")) {
            f += "02";
        } else if (month.equals("Apr")) {
            f += "03";
        } else if (month.equals("May")) {
            f += "04";
        } else if (month.equals("Jun")) {
            f += "05";
        } else if (month.equals("Jul")) {
            f += "06";
        } else if (month.equals("Aug")) {
            f += "07";
        } else if (month.equals("Sep")) {
            f += "08";
        } else if (month.equals("Oct")) {
            f += "09";
        } else if (month.equals("Nov")) {
            f += "10";
        } else {
            f += "11";
        }
        f += s.substring(0, 2);
        String h = s.substring(10, 12);
        int hr = Integer.parseInt(h);
        if (s.charAt(19) == 'P') {
            hr += 12;
        }
        String hour = String.valueOf(hr);
        if (hour.length() == 1) {
            f += "0";
        }
        f += hour;
        f += s.substring(13, 15);
        return f;
    }
    //END
}
