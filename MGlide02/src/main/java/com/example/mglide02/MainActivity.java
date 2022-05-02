package com.example.mglide02;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mglide02.glide.HGlide;
import com.example.mglide02.glide.RequestCall;


public class MainActivity extends AppCompatActivity {

    String[] images;
    LinearLayout mLinearImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearImages = findViewById(R.id.ll_images);
        findViewById(R.id.btn_load_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < images.length; i++) {
                    ImageView imageView = new ImageView(MainActivity.this);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
                    imageView.setImageResource(R.mipmap.loading_progress);
                    mLinearImages.addView(imageView);
                    RequestCall requestCall = HGlide.with(MainActivity.this).load(images[i]).into(imageView);
                    HGlide.getInstance().startRequest(requestCall);
                }
            }
        });
        findViewById(R.id.btn_pause_load_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGlide.getInstance().pauseRequest(null);
            }
        });
        findViewById(R.id.btn_resume_load_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGlide.getInstance().resumeRequest(null);
            }
        });
        images = getResources().getStringArray(R.array.image_urls);
    }


}