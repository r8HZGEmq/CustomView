package com.emo.customview.timeticker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emo.customview.R;
import com.emo.customview.titleBar.TitlebarActivity;

/**
 * Created by emo on 17/3/8.
 */

public class TimeTickerActivity extends Activity {
    TimeTickerWidget mTimeTickerWidget;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_ticker);
        mTimeTickerWidget = (TimeTickerWidget) findViewById(R.id.timeticker);
        mTimeTickerWidget.start(1488958219000l);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(TimeTickerActivity.this, TitlebarActivity.class);
                TimeTickerActivity.this.startActivity(it);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTimeTickerWidget != null) {
            mTimeTickerWidget.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeTickerWidget != null) {
            mTimeTickerWidget.cancel();
        }
    }
}
