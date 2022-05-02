package com.example.mglide02.glide;

public interface IGlide {
    /**
     * 开启网络请求
     *
     * @param request
     */
    void startRequest(RequestCall request);

    /**
     * 暂停网络请求
     *
     * @param request
     */
    void pauseRequest(RequestCall request);

    /**
     * 回去网络请求
     *
     * @param request
     */
    void resumeRequest(RequestCall request);

    /**
     * 取消网络请求
     *
     * @param request
     */
    void cancelRequest(RequestCall request);

    /**
     * 结束网络请求
     *
     * @param request
     */
    void finishRequest(RequestCall request);
}
