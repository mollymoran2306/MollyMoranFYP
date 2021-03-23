package com.example.MollyMoranFYP.Activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.squareup.picasso.Picasso;

public class FullScreenImage extends AppCompatActivity {

    private ImageView imageView, profilePic;
    private TextView username, subject, messagetext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmessage_fullscreen);
        imageView = findViewById(R.id.imageView2);

        String url = getIntent().getStringExtra("image_url");
        if (url != null) {
            Picasso.get().load(url).into(imageView);
        }

        getSupportActionBar().hide();

        profilePic = findViewById(R.id.profilePic);

        if (!getIntent().getStringExtra("profile pic").equals("")) {
            String prof = getIntent().getStringExtra("profile pic");
            Picasso.get().load(prof).into(profilePic);
        }

        username = findViewById(R.id.username);
        String usr = getIntent().getStringExtra("sender");
        username.setText(usr);

        subject = findViewById(R.id.subject);
        String sub = getIntent().getStringExtra("subject");
        subject.setText(sub);

        messagetext = findViewById(R.id.messagetext);
        String msg = getIntent().getStringExtra("message");
        messagetext.setText(msg);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showActionsDialog();
                return true;

            }
        });


    }

    private void showActionsDialog() {
        CharSequence colors[] = new CharSequence[]{"Save Image", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    saveImage();
                } else {
                    dialog.cancel();
                }
            }
        });
        builder.show();
    }

    private void saveImage() {
        Bitmap b = imageView.getDrawingCache();
        MediaStore.Images.Media.insertImage(FullScreenImage.this.getContentResolver(), b, null, null);

    }
}
