package com.example.mglide02.glide;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GlideImpl implements IGlide {
    private static final String TAG = "HGlide=====";
    private int maxRequests = 2;
    private final Queue<RequestCall> readyAsyncRequest = new ArrayDeque<>();
    private final HashMap<Integer, RequestCall> runningAsyncRequest = new HashMap<>();
    private ExecutorService executorService;

    @Override
    public void startRequest(RequestCall request) {
        if (runningAsyncRequest.size() < maxRequests) {
            runningAsyncRequest.put(request.hashCode(), request);
            request.setOnCall(new RequestCall.OnCall() {
                @Override
                public void onRequestFinish() {
                    runningAsyncRequest.remove(request.hashCode());
                    refreshRequest();
                }
            });
            executorService().execute(request);
        } else {
            readyAsyncRequest.add(request);
        }
    }

    private void refreshRequest() {
        if (runningAsyncRequest.size() < maxRequests) {
            RequestCall requestCall = readyAsyncRequest.poll();
            if (requestCall != null) {
                startRequest(requestCall);
            }
        }
    }

    @Override
    public void pauseRequest(RequestCall request) {
        if (request == null) {
            for (RequestCall requestCall : runningAsyncRequest.values()) {
                requestCall.pause();
            }
            RequestCall requestCall;
            while ((requestCall = readyAsyncRequest.peek()) != null) {
                requestCall.pause();
            }
            return;
        }
        RequestCall requestCall = runningAsyncRequest.get(request.hashCode());
        if (requestCall != null) {
            requestCall.pause();
        }
    }

    @Override
    public void resumeRequest(RequestCall request) {
        if (request == null) {
            for (RequestCall requestCall : runningAsyncRequest.values()) {
                requestCall.resume();
            }
            RequestCall requestCall;
            while ((requestCall = readyAsyncRequest.peek()) != null) {
                requestCall.resume();
            }
            return;
        }
        RequestCall requestCall = runningAsyncRequest.get(request.hashCode());
        if (requestCall != null) {
            requestCall.resume();
        }
    }

    @Override
    public void cancelRequest(RequestCall request) {
        RequestCall requestCall = runningAsyncRequest.get(request.hashCode());
        if (requestCall != null) {
            requestCall.pause();
            runningAsyncRequest.remove(request.hashCode());
        }
    }

    @Override
    public void finishRequest(RequestCall request) {
        RequestCall requestCall = runningAsyncRequest.get(request.hashCode());
        if (requestCall != null) {
            requestCall.finish();
            runningAsyncRequest.remove(request.hashCode());
        }
    }


    public synchronized ExecutorService executorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("HGlide");
                    return thread;
                }
            });
        }
        return executorService;
    }
}
