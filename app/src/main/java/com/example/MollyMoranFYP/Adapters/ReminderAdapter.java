package com.example.MollyMoranFYP.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Reminder> reminderList;


    public ReminderAdapter(Context mContext, ArrayList<Reminder> reminderList) {
        this.mContext = mContext;
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder r = reminderList.get(position);
        holder.reltitle.setText(r.getTitle());
        holder.reldate.setText(parseDate(r.getDate()));
        holder.reltime.setText(parseTime(r.getTime()));
        holder.reldesc.setText(r.getDes());
    }

    @Override
    public int getItemCount() {
        if (reminderList == null) return 0;
        return reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reltitle;
        TextView reltime;
        TextView reldate;
        TextView reldesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reltitle = itemView.findViewById(R.id.reltitle);
            reltime = itemView.findViewById(R.id.reltime);
            reldate = itemView.findViewById(R.id.reldate);
            reldesc = itemView.findViewById(R.id.reldesc);

        }
    }

    // formatting the date
    public String parseDate(String d) {
        if (d.equals("---")) {
            return d;
        }
        String year = d.substring(0, 4), m = d.substring(4, 6), day = d.substring(6, 8);
        int month = Integer.parseInt(m) + 1;
        return day + "/" + month + "/" + year;
    }

    // formatting the time
    public String parseTime(String d) {
        if (d.equals("---")) {
            return d;
        }
        String h = d.substring(0, 2), m = d.substring(2, 4);
        int hr = Integer.parseInt(h);
        Boolean pm = false;
        if (hr >= 12) {
            pm = true;
            hr %= 12;
            if (hr == 0)
                hr = 12;
        }
        h = String.valueOf(hr);
        return h + ":" + m + (pm ? " PM" : " AM");
    }
}
