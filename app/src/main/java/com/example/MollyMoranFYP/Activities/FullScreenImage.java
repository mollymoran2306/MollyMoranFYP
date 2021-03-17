package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.squareup.picasso.Picasso;

public class FullScreenImage extends AppCompatActivity {

    private ImageView imageView, profilePic;
    private TextView username, subject, messagetext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image);
        imageView = findViewById(R.id.imageView2);
        String url = getIntent().getStringExtra("image_url");
        Picasso.get().load(url).into(imageView);
        getSupportActionBar().hide();

        profilePic = findViewById(R.id.profilePic);
        String prof = getIntent().getStringExtra("profile pic");
        Picasso.get().load(prof).into(profilePic);

        username = findViewById(R.id.username);
        String usr = getIntent().getStringExtra("sender");
        username.setText(usr);

        subject = findViewById(R.id.subject);
        String sub = getIntent().getStringExtra("subject");
        subject.setText(sub);

        messagetext = findViewById(R.id.messagetext);
        String msg = getIntent().getStringExtra("message");
        messagetext.setText(msg);



    }
}
