package com.emo.customview.titleBar;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.emo.customview.R;
import com.emo.customview.timeticker.TimeTickerWidget;

/**
 * Created by emo on 17/3/8.
 */

public class TitlebarActivity extends Activity implements ISDKTitleBar {
    SDKTitleBar mSDKTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titlebar);
        mSDKTitleBar = (SDKTitleBar) findViewById(R.id.sdk_titlebar);
        mSDKTitleBar.setSDKTitlebarListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTitleClicked() {
        showToast("click title");
    }

    @Override
    public void onLeftClicked() {
        showToast("click left");
    }

    @Override
    public void onRightClicked() {
        showToast("click right");
    }

    void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
