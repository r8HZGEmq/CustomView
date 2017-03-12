package com.emo.customview.loadlayout;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.emo.customview.BaseApplication;
import java.lang.reflect.Field;
/**
 * Created by emo on 17/3/9.
 *
 * 设备信息工具类,用以获取屏幕分辨率,设备识别号等硬件信息
 */
public class DeviceUtil {
    private static int screenWidth = 0;
    private static int screenHeight = 0;
    private static int statusBarHeight = 0;

    public static boolean isTablet = false;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获得状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            Class<?> c;
            Object obj;
            Field field;
            int x;
            try {
                c = Class.forName("com.android.internal.R$dimen");
                obj = c.newInstance();
                field = c.getField("status_bar_height");
                x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = BaseApplication.getAppContext().getResources().getDimensionPixelSize(x);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
//
//            View view = activity.getWindow().getDecorView();
//            Rect rect = new Rect();
//            view.getWindowVisibleDisplayFrame(rect);
//            statusBarHeight = rect.top;
        }
        return statusBarHeight;
    }

    public static int getScreenWidth() {
        if (screenWidth == 0) {
            screenWidth = BaseApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
        }
        return screenWidth;
    }

    public static DisplayMetrics getDisplayMetrics() {
        return BaseApplication.getAppContext().getResources().getDisplayMetrics();
    }

    public static int getScreenHeight() {
        if (screenHeight == 0) {
            screenHeight = BaseApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
        }
        return screenHeight;
    }

    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 判断是否平板
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        double screenSize = diagonalPixels / (160 * dm.density);
        if (screenSize >= 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断sdk版本是否高于11.目前关系到属性动画的使用
     *
     * @return
     */
    public static boolean isHigherThanSDK11() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return true;
        }
        return false;
    }

    /**
     * 获取手机厂商
     * @return
     */
    public static String getDeviceManufacturers(){
        return Build.MANUFACTURER;
    }


}
