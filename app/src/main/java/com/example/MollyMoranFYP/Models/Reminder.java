package com.example.MollyMoranFYP.Models;

public class Reminder {
    private String title, des, date, time, repeat, full, marker;

    public Reminder() {
    }

    public Reminder(String title, String des, String date, String time, String repeat, String full) {
        this.title = title;
        this.des = des;
        this.date = date;
        this.time = time;
        this.repeat = repeat;
        this.full = full;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }


}
