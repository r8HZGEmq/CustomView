package com.emo.customview.timeticker;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emo.customview.R;

/**
 * Created by emo on 2017/03/08.
 */
public class TimeTickerWidget extends LinearLayout {

    private TickTimer timer;
    private long lastReadTime;
    private long totalLeavingTime;

    private int mTextSyle = 0;
    private Context mContext;
    private int type = CART_FLAG;
    private String flag = "";
    public static final int CART_FLAG = 0;
    public static final int ORDER_FLAG = 1;

    private TextView title, h1, h2, m1, m2, s1, s2; // 时分秒

    public TimeTickerWidget(Context context) {
        super(context);
        mContext = context;
        initWidgets(context);
    }

    public TimeTickerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initWidgets(context);
    }

    public TimeTickerWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initWidgets(context);
    }

    private void initWidgets(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View convertView = inflater.inflate(R.layout.widget_timer_ticker, this, true);
        title = (TextView) convertView.findViewById(R.id.title);
        h1 = (TextView) convertView.findViewById(R.id.h1);
        h2 = (TextView) convertView.findViewById(R.id.h2);
        m1 = (TextView) convertView.findViewById(R.id.m1);
        m2 = (TextView) convertView.findViewById(R.id.m2);
        s1 = (TextView) convertView.findViewById(R.id.s1);
        s2 = (TextView) convertView.findViewById(R.id.s2);
    }

    public void setTitle(String param) {
        if (TextUtils.isEmpty(param) || TextUtils.isEmpty(param.trim())) {
            throw new RuntimeException("title not allowed null");
        } else if (param.length() > 2) {
            title.setText(new StringBuilder(param.substring(0, 2)).append("\n").append(param.substring(2, 4)).toString());
        } else {
            title.setText(param);
        }
    }

    /**
     * must call before onDisplay()
     *
     * @param time original count-down-time,in second
     */
    public void init(long time) {
        totalLeavingTime = time;
        lastReadTime = SystemClock.elapsedRealtime(); //开机的时间到当前的时间
    }

    /**
     * call in onDisplay()
     */
    public void start() {
        if (timer != null) timer.cancel();
        if (totalLeavingTime > 0L) {
            timer = new TickTimer(totalLeavingTime * 1000, 100);
            timer.start();
        }
    }

    /**
     * 开始倒计时
     *
     * @param time 剩余时间
     */
    public void start(long time) {
        init(time);
        start();
    }

    public void start(long time, int type, String flag) {
        this.type = type;
        this.flag = flag;
        start(time);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        totalLeavingTime = 0L;
    }

    /**
     * call in onBack() / onLeave()
     */
    public void cancel() {
        if (timer != null) {
            totalLeavingTime = timer.leaving;
            timer.cancel();
            timer = null;
        }
    }

    private class TickTimer extends CountDownTimer {
        int day, hour, min, sec, ms = 9;
        long leaving;

        public TickTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            long diff = millisInFuture / 1000;
            sec = (int) (diff % 60);
            min = (int) (diff / 60 % 60);
            hour = (int) (diff / (60 * 60) % 24);
            day = (int) (diff / (24 * 60 * 60));
            setClockText(day * 24 + hour, min, sec, ms);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            leaving = millisUntilFinished;
            int d = day, h = hour, m = min, s = sec, ls = ms;
            if (ls-- == 0) {
                ls = 9;
                if (s-- == 0) {
                    s = 59;
                    if (m-- == 0) {
                        m = 59;
                        if (h-- == 0) {
                            h = 23;
                            if (d-- == 0) {
                                setVisibility(View.GONE);
                                return;
                            }
                        }
                    }
                }
            }
            setClockText(d * 24 + h, m, s, ls);
            day = d;
            hour = h;
            min = m;
            sec = s;
            ms = ls;
        }

        private void setClockText(int hour, int min, int sec, int ms) {
            String hourStr = formatTime(hour);
            String minStr = formatTime(min);
            String secStr = formatTime(sec);
            String msStr = formatTime(ms);
            if (hour < 1) {
                if (h1 != null) {
                    h1.setText(minStr.substring(0, 1));
                    h2.setText(minStr.substring(1, 2));
                    m1.setText(secStr.substring(0, 1));
                    m2.setText(secStr.substring(1, 2));
                    s1.setText(msStr.substring(0, 1));
                    s2.setText(msStr.substring(1, 2));
                }
            } else {
                if (h1 != null) {
                    h1.setText(hourStr.substring(0, 1));
                    h2.setText(hourStr.substring(1, 2));
                    m1.setText(minStr.substring(0, 1));
                    m2.setText(minStr.substring(1, 2));
                    s1.setText(secStr.substring(0, 1));
                    s2.setText(secStr.substring(1, 2));
                }
            }

        }

        @Override
        public void onFinish() {
            setVisibility(View.GONE);
        }
    }

    String formatTime(int t) {
        if (t < 10) {
            return "0" + t;
        }
        return Integer.toString(t);
    }
}
