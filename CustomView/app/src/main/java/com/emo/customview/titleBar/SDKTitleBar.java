package com.emo.customview.titleBar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emo.customview.R;

public class SDKTitleBar extends RelativeLayout implements View.OnClickListener {

    public static final int ICON_ALIGN_LEFT = 0;
    public static final int ICON_ALIGN_RIGHT = 1;

    protected TextView mLeftSide_TV;
    protected TextView mRightSide_TV;
    protected TextView mTitle_TV;
    protected int mLeftIconSize;
    protected int mRightIconSize;
    protected int mLeftIconAlign = ICON_ALIGN_LEFT;
    protected int mRightIconAlign = ICON_ALIGN_RIGHT;
    protected ISDKTitleBar mISDKTitleBar;
    public SDKTitleBar(Context context) {
        super(context);
        init(null, 0);
    }

    public SDKTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SDKTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.widget_titlebar, this);

        setBackgroundColor(Color.WHITE);


        mLeftSide_TV = (TextView) findViewById(R.id.titlebar_left_tv);
        mRightSide_TV = (TextView) findViewById(R.id.titlebar_right_tv);
        mTitle_TV = (TextView) findViewById(R.id.titlebar_title_tv);

        int defaultIconSize;
        try {
            defaultIconSize = getResources().getDimensionPixelSize(R.dimen.titlebar_subtitle_icon_size);
        } catch (Exception e) {
            defaultIconSize = (int) (25 * getResources().getDisplayMetrics().density);
        }
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SDKTitleBar, defStyle, 0);
        String leftText = a.getString(R.styleable.SDKTitleBar_leftLabel);
        ColorStateList leftColorStateList = a.getColorStateList(R.styleable.SDKTitleBar_leftTextColor);
        Drawable leftIcon = a.getDrawable(R.styleable.SDKTitleBar_leftIcon);
        mLeftIconSize = a.getDimensionPixelSize(R.styleable.SDKTitleBar_leftIconSize, defaultIconSize);
        mLeftIconAlign = a.getInt(R.styleable.SDKTitleBar_leftIconAlign, ICON_ALIGN_LEFT);

        String rightText = a.getString(R.styleable.SDKTitleBar_rightLabel);
        Drawable rightIcon = a.getDrawable(R.styleable.SDKTitleBar_rightIcon);
        ColorStateList rightColorStateList = a.getColorStateList(R.styleable.SDKTitleBar_rightTextColor);
        mRightIconSize = a.getDimensionPixelSize(R.styleable.SDKTitleBar_rightIconSize, defaultIconSize);
        mRightIconAlign = a.getInt(R.styleable.SDKTitleBar_rightIconAlign, ICON_ALIGN_RIGHT);

        String title = a.getString(R.styleable.SDKTitleBar_title);
        ColorStateList titleColorStateList = a.getColorStateList(R.styleable.SDKTitleBar_titleTextColor);
        a.recycle();

        mLeftSide_TV.setOnClickListener(this);
        mRightSide_TV.setOnClickListener(this);
        mTitle_TV.setOnClickListener(this);

        if (null != leftColorStateList) {
            setLeftTextColor(leftColorStateList);
        }
        setLeft(leftText, leftIcon, mLeftIconAlign);

        if (null != rightColorStateList) {
            setRightTextColor(rightColorStateList);
        }
        setRight(rightText, rightIcon, mRightIconAlign);

        if (null != titleColorStateList) {
            setTitleTextColor(titleColorStateList);
        }
        setTitle(title);
    }

    public void setSDKTitlebarListener(ISDKTitleBar listener) {
        mISDKTitleBar = listener;
    }

    /**
     * 设置title
     */
    public void setTitle(String text) {
        mTitle_TV.setText(text);
    }

    /**
     * 设置title
     */
    public void setTitle(int textRes) {
        checkAndSetText(mTitle_TV, textRes);
    }

    public void setTitleTextColor(int color) {
        mTitle_TV.setTextColor(color);
    }

    public void setTitleTextColor(ColorStateList colors) {
        mTitle_TV.setTextColor(colors);
    }

    public void setTitleVisibility(int visibility) {
        mTitle_TV.setVisibility(visibility);
    }

    /**
     * 设置左侧按钮文本、icon、icon和文字排版方式
     * @param text 如果没有文本，赋值null
     * @param icon 如果没有icon，赋值null
     * @param align {@link #ICON_ALIGN_LEFT} 和 {@link #ICON_ALIGN_RIGHT}
     *
     * @see #ICON_ALIGN_LEFT
     * @see #ICON_ALIGN_RIGHT
     */
    public void setLeft(String text, Drawable icon, int align) {
        mLeftSide_TV.setText(text);
        checkAlign(mLeftSide_TV, resizeDrawable(icon, mLeftIconSize, mLeftIconSize), align);
    }

    /**
     * 设置左侧按钮文本、icon、icon和文字排版方式
     * @param textRes 如果没有文本，赋值0
     * @param iconRes 如果没有icon，赋值0
     * @param align {@link #ICON_ALIGN_LEFT} 和 {@link #ICON_ALIGN_RIGHT}
     *
     * @see #ICON_ALIGN_LEFT
     * @see #ICON_ALIGN_RIGHT
     */
    public void setLeft(int textRes, int iconRes, int align) {
        checkAndSetText(mLeftSide_TV, textRes);
        checkAlign(mLeftSide_TV, resizeDrawable(iconRes, mLeftIconSize, mLeftIconSize), align);
    }

    public void setLeftTextColor(int color) {
        mLeftSide_TV.setTextColor(color);
    }

    public void setLeftTextColor(ColorStateList colors) {
        mLeftSide_TV.setTextColor(colors);
    }

    public void setLeftVisibility(int visibility) {
        mLeftSide_TV.setVisibility(visibility);
    }

    /**
     * 设置右侧按钮文本、icon、icon和文字排版方式
     * @param text 如果没有文本，赋值null
     * @param icon 如果没有icon，赋值null
     * @param align {@link #ICON_ALIGN_LEFT} 和 {@link #ICON_ALIGN_RIGHT}
     *
     * @see #ICON_ALIGN_LEFT
     * @see #ICON_ALIGN_RIGHT
     */
    public void setRight(String text, Drawable icon, int align) {
        mRightSide_TV.setText(text);
        checkAlign(mRightSide_TV, resizeDrawable(icon, mRightIconSize, mRightIconSize), align);
    }

    /**
     * 设置右侧按钮文本、icon、icon和文字排版方式
     * @param textRes 如果没有文本，赋值0
     * @param iconRes 如果没有icon，赋值0
     * @param align {@link #ICON_ALIGN_LEFT} 和 {@link #ICON_ALIGN_RIGHT}
     *
     * @see #ICON_ALIGN_LEFT
     * @see #ICON_ALIGN_RIGHT
     */
    public void setRight(int textRes, int iconRes, int align) {
        checkAndSetText(mRightSide_TV, textRes);
        checkAlign(mRightSide_TV, resizeDrawable(iconRes, mRightIconSize, mRightIconSize), align);
    }

    public void setRightTextColor(int color) {
        mRightSide_TV.setTextColor(color);
    }

    public void setRightTextColor(ColorStateList colors) {
        mRightSide_TV.setTextColor(colors);
    }

    public void setRightVisibility(int visibility) {
        mRightSide_TV.setVisibility(visibility);
    }

    protected void checkAlign(TextView view, Drawable icon, int align) {
        switch (align) {
            case ICON_ALIGN_RIGHT:
                view.setCompoundDrawables(null, null, icon, null);
                break;
            case ICON_ALIGN_LEFT:
            default:
                view.setCompoundDrawables(icon, null, null, null);
                break;
        }
    }

    protected void checkAndSetText(TextView tv, int textRes) {
        if (textRes <= 0) {
            tv.setText(null);
        } else {
            tv.setText(textRes);
        }
    }

    protected Drawable resizeDrawable(int iconRes, int maxWidth, int maxHeight) {
        if (iconRes <= 0) {
            return null;
        }
        return resizeDrawable(getResources().getDrawable(iconRes), maxWidth, maxHeight);
    }

    protected Drawable resizeDrawable(Drawable drawable, int maxWidth, int maxHeight) {
        if (null != drawable) {
            float dWidth = drawable.getIntrinsicWidth();
            float dHeight = drawable.getIntrinsicHeight();
            float widthScale = maxWidth / dWidth;
            float heightScale = maxHeight/ dHeight;
            float scale = Math.min(widthScale, heightScale);

            int targetWidth = (int) (scale * dWidth + 0.5f);
            int targetHeight = (int) (scale * dHeight + 0.5f);
            drawable.setBounds(0, 0, targetWidth, targetHeight);
        }
        return drawable;
    }

    @Override
    public void onClick(View v) {
        if (null == mISDKTitleBar) {
            return ;
        }
        if (mTitle_TV == v) {
            mISDKTitleBar.onTitleClicked();
        } else if (mLeftSide_TV == v) {
            mISDKTitleBar.onLeftClicked();
        } else if (mRightSide_TV == v) {
            mISDKTitleBar.onRightClicked();
        }
    }
}
