package com.example.MollyMoranFYP.Models;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Date;

public class MedicationReminder extends AppCompatActivity {
    private int medicationID;
    private Date reminderDate;
    private Time reminderTime;

    public int getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(int medicationID) {
        this.medicationID = medicationID;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Time getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Time reminderTime) {
        this.reminderTime = reminderTime;
    }
}
