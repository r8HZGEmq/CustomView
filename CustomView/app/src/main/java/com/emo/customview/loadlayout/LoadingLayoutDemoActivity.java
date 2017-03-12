package com.emo.customview.loadlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.emo.customview.R;

/**
 * Created by emo on 17/3/9.
 */
public class LoadingLayoutDemoActivity extends Activity implements View.OnClickListener {
    private LoadingLayout loadinglayout;
    private LoadingLayoutDemoActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadinglayoutactivity);
        mContext = this;
        loadinglayout = (LoadingLayout) findViewById(R.id.loadinglayout);
        loadinglayout.setRefreshClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesetData();
            }
        });
        requesetData();
    }

    // 加载资讯类型数据
    private void requesetData() {
        /*if(网络返回数据空){
            loadinglayout.showNoDataError();
        }else if(网络加载失败){
            loadinglayout.showError();
        }else(请求成功){
            loadinglayout.removeProcess();
        }*/
    }

    @Override
    public void onClick(View view) {
        requesetData();
    }
}
