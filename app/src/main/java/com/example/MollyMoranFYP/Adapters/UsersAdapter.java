package com.example.MollyMoranFYP.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.MollyMoranFYP.Models.User;
import com.example.MollyMoranFYP.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class UsersAdapter extends   RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<User> userList;
    private String i;

    private static final String TAG = "*UsersAdapter*";

    public UsersAdapter(Context mContext, ArrayList<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_manage_users_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User r = userList.get(position);

        holder.txtUsername.setText(r.getName());
        Log.d(TAG, "Name is " + r.getName());
        holder.userType.setText("User Type: " + r.getUserType());
        Log.d(TAG, "Type is " + r.getUserType());
        if (r.getProfilePic()!= null ) {
            Picasso.get().load(r.getProfilePic()).into(holder.userImage);
        } else {
            Picasso.get().load(R.drawable.users).into(holder.userImage);
        }
        Log.d(TAG, "Image is" + r.getProfilePic());
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUsername;
        TextView userType;
        ImageView userImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtUsername);
            userType = itemView.findViewById(R.id.userType);
            userImage = itemView.findViewById(R.id.userImage);

        }
    }
}



