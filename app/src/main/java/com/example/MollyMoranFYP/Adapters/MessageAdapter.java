package com.example.MollyMoranFYP.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Activities.FullScreenImage;
import com.example.MollyMoranFYP.Activities.ViewMessagesActivity;
import com.example.MollyMoranFYP.Models.Message;
import com.example.MollyMoranFYP.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Message> messageList;
    private String i;
    DatabaseReference reff;

    private static final String TAG = "*MessageAdapter*";

    public MessageAdapter(Context mContext, ArrayList<Message> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_viewmessages_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        
        final Message r = messageList.get(position);
        Log.d(TAG, "message r is " + r);

        holder.relsubject.setText(r.getSubject());
        Log.d(TAG, "Subject is " + r.getSubject());

        holder.relmessagetext.setText(r.getMessageText());

        Log.d(TAG, "r.getImage() is " + r.getImage());

        if (r.getImage() == null) {

        } else {
            Picasso.get().load(r.getImage()).into(holder.messageImage);
        }

        holder.txtUsername.setText(r.getSender());
        Log.d(TAG, "r.getSender is " + r.getSender());

        if (r.getProfilePic().equals("")) {
            Picasso.get().load(R.drawable.users).into(holder.messageImage);
        } else {
            Picasso.get().load(r.getProfilePic()).into(holder.profilePic);
        }

        Log.d(TAG, "r.getProfilePic is " + r.getProfilePic());

        holder.txtDate.setText(parseDate(r.getDate()));
        holder.txtTime.setText(parseTime(r.getTime()));


        holder.messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext, FullScreenImage.class);
                intent.putExtra("image_url", r.getImage());
                intent.putExtra("profile pic", r.getProfilePic());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                Log.d(TAG, "image url is " + r.getImage());
                Log.d(TAG, "profile pic is " + r.getProfilePic());
            }
        });

//        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = cursor.getUid();
//
//        reff= FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(uid)
//                .child("Usernames")
//                .child(r.getSender());
//
//        Log.d(TAG, "Ref is" + reff);
//        Log.d(TAG, "Sender is " + r.getSender());

//        reff.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("Name") ) {
//                    String name = dataSnapshot.child("Name").getValue(String.class);
//                    holder.txtUsername.setText(name);
//                }
//
//                if (dataSnapshot.hasChild("Profile Pic") ) {
//                    String pic = dataSnapshot.child("Profile Pic").getValue(String.class);
//                    Log.d(TAG, "Profile Pic string is " + pic);
//                    Picasso.get().load(pic).into(holder.profilePic);
//               }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (messageList == null) return 0;
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername, txtDate, txtTime;
        TextView relsubject;
        TextView relmessagetext;
        ImageView messageImage, profilePic;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relsubject = itemView.findViewById(R.id.relsubject);
            relmessagetext = itemView.findViewById(R.id.relmessagetext);
            messageImage = itemView.findViewById(R.id.messageImage);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            profilePic = itemView.findViewById(R.id.profilePic);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
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
