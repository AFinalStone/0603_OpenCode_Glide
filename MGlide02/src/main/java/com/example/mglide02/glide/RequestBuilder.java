package com.example.mglide02.glide;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class RequestBuilder {
    private Context context;
    private String url;
    private WeakReference<ImageView> weakImageview;

    public RequestBuilder(Context context) {
        this.context = context;
    }

    public RequestBuilder load(String url) {
        this.url = url;
        return this;
    }

    public RequestCall into(ImageView imageView) {
        this.weakImageview = new WeakReference<>(imageView);
        return new RequestCall(this);
    }

    protected Context getContext() {
        return context;
    }

    protected String getUrl() {
        return url;
    }

    protected ImageView getWeakImageview() {
        ImageView imageView = null;
        if (weakImageview != null) {
            imageView = weakImageview.get();
        }
        return imageView;
    }
}