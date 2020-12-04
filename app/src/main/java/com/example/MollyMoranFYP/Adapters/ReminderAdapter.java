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
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
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
        holder.reldate.setText(r.getDate());
        holder.reltime.setText(r.getTime());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return reminderList == null ? 0 : reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reltitle;
        TextView reltime;
        TextView reldate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reltitle = itemView.findViewById(R.id.reltitle);
            reltime = itemView.findViewById(R.id.reltime);
            reldate = itemView.findViewById(R.id.reldate);

        }
    }
}
