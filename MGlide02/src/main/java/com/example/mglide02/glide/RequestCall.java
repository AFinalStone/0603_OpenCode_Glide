package com.example.mglide02.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 请求参数
 */
public class RequestCall implements Runnable {

    public interface OnCall {
        void onRequestFinish();
    }

    private static final String TAG = "RequestCall=====";
    private static Handler mMainLooperHandler = new Handler(Looper.getMainLooper());
    RequestBuilder requestBuilder;
    HttpURLConnection mHttpURLConnection;
    private OnCall onCallListener;
    private boolean isPause;
    private boolean isRunning;

    public RequestCall(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public void setOnCall(OnCall onCallListener) {
        this.onCallListener = onCallListener;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                SystemClock.sleep(2000);
                if (isPause) {
                    this.wait();
                }
                if (isRunning) {
                    return;
                }
                mHttpURLConnection = (HttpURLConnection) new URL(requestBuilder.getUrl()).openConnection();
                mHttpURLConnection.setRequestMethod("GET");
                mHttpURLConnection.setConnectTimeout(5000);
                mHttpURLConnection.setConnectTimeout(5000);
                mHttpURLConnection.connect();
                int code = mHttpURLConnection.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = mHttpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mMainLooperHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ImageView imageView = requestBuilder.getWeakImageview();
                            if (imageView != null) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "图片下载失败");
                }
                mHttpURLConnection.disconnect();
                if (onCallListener != null) {
                    onCallListener.onRequestFinish();
                }
                isPause = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPause = true;
    }

    public void resume() {
        synchronized (this) {
            notify();
        }
        isPause = false;
        notify();
    }

    public void finish() {
        isRunning = false;
    }
}
