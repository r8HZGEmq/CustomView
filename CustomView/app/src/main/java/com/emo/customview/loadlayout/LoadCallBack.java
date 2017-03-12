package com.emo.customview.loadlayout;
/**
 * Created by emo on 17/3/9.
 */
public interface LoadCallBack {
    void onLoadingStart();

    void onLoadingFinish();

    void onLoadFail(int code,String msg);
}
