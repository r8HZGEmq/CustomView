package com.emo.customview;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

/**
 * Created by emo on 17/3/9.
 */


public class BaseApplication extends Application {

    private static Handler sHandler;
    private static Application sApplication;
    public static final String API_TOKEN_ERROR = "api_token_error";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public BaseApplication() {
        sApplication = this;
    }

    /**
     * @return 整个APP可以使用的Handler（为主线程）
     */
    public static Handler getHandler() {
        checkHandler();
        return sHandler;
    }

    /**
     * @return 整个APP可以使用的Context
     */
    public static Application getAppContext() {
        checkNull(sApplication);
        return sApplication;
    }

    private static void checkNull(Object obj) {
        if (null == obj) {
            throw new RuntimeException("check whether the app has a Application "
                    + "class extends BaseApplication ? or forget to "
                    + "invoke super class's constructor first!");
        }
    }

    private static void checkHandler() {
        if (null == sHandler) {
            sHandler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * 强杀本进程
     */
    public static void killProcess() {
        Process.killProcess(Process.myPid());
    }
}
