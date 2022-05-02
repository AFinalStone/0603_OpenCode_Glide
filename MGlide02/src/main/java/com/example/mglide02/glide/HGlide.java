package com.example.mglide02.glide;

import android.content.Context;

public class HGlide implements IGlide {

    IGlide iGlide;

    static HGlide INSTANCE;

    public static HGlide getInstance() {
        if (INSTANCE == null) {
            synchronized (HGlide.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HGlide();
                }
            }
        }
        return INSTANCE;
    }

    private HGlide() {
        this.iGlide = new GlideImpl();
    }

    public static RequestBuilder with(Context context) {
        return new RequestBuilder(context);
    }

    @Override
    public void startRequest(RequestCall request) {
        iGlide.startRequest(request);
    }

    @Override
    public void pauseRequest(RequestCall request) {
        iGlide.pauseRequest(request);
    }

    @Override
    public void resumeRequest(RequestCall request) {
        iGlide.resumeRequest(request);
    }

    @Override
    public void cancelRequest(RequestCall request) {
        iGlide.cancelRequest(request);
    }

    @Override
    public void finishRequest(RequestCall request) {
        iGlide.finishRequest(request);
    }
}
