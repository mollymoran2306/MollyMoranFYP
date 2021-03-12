package com.example.MollyMoranFYP.Activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MollyMoranFYP.R;
import com.squareup.picasso.Picasso;

public class FullScreenImage extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image);
        imageView = findViewById(R.id.imageView);
        String url = getIntent().getStringExtra("image_url");

        Picasso.get().load(url).into(imageView);

    }
}
