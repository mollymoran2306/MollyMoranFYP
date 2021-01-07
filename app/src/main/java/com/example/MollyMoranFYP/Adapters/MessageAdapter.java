package com.example.MollyMoranFYP.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Models.Image;
import com.example.MollyMoranFYP.Models.Message;
import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Message> messageList;
    private String i;

    private static final String TAG = "*MessageAdapter*";

    public MessageAdapter(Context mContext, ArrayList<Message> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message r = messageList.get(position);
        holder.relsubject.setText(r.getSubject());
        holder.relmessagetext.setText(r.getMessageText());
        holder.messageImage.setImageURI(Uri.parse(r.getImage()));

    }

    @Override
    public int getItemCount() {
        if (messageList == null) return 0;
        return messageList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView relsubject;
        TextView relmessagetext;
        ImageView messageImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relsubject = itemView.findViewById(R.id.relsubject);
            relmessagetext = itemView.findViewById(R.id.relmessagetext);
            messageImage = itemView.findViewById(R.id.messageImage);

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
