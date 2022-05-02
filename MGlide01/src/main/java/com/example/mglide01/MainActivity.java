package com.example.mglide01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity=====";
    String[] images = {"http://newsimg.5054399.com/uploads/userup/1801/191K504F05.jpg"
            , "http://newsimg.5054399.com/uploads/userup/1801/2209223L125.jpg"
            , "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fp8.itc.cn%2Fq_70%2Fimages03%2F20210621%2Fe8b4f3f2d5ea400fa770b4217ad43e85.jpeg&refer=http%3A%2F%2Fp8.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654077315&t=cc1b90ac0a7da75189df7e877379f15e"};
    ImageView[] imageViews = new ImageView[3];
    Handler mMainLooperHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViews[0] = findViewById(R.id.image00);
        imageViews[1] = findViewById(R.id.image01);
        imageViews[2] = findViewById(R.id.image02);
        findViewById(R.id.btn_load_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < images.length; i++) {
                    loadUrl(imageViews[i], images[i]);
                }
            }
        });
    }

    private void loadUrl(ImageView imageView, String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.connect();
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        mMainLooperHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });

                    } else {
                        Log.d(TAG, "图片下载失败");
                    }
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}