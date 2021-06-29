package com.example.flickr_mvp.Activities;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.flickr_mvp.R;


public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_details);

        String img_url = getIntent().getStringExtra(getBaseContext().getString(R.string.img_url));
        ImageView iw = findViewById(R.id.imageViewer);

        Glide.with(this)
                .load(img_url)
                .into(iw);
    }
}