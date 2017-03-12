package com.emo.customview.loadlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.emo.customview.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
/**
 * Created by emo on 17/3/9.
 */
public class LoadingLayout extends FrameLayout implements LoadCallBack {
    private View loadingLayout;
    private View errorLayout, nodataLayout;
    private TextView errorTextView;
    private OnClickListener mRefreshClickListener;
    private Context context;
    private int loadinglayoutId;
    private int errorLayoutId;
    private int nodataLayoutId;
    private static final String LoadingGifFilePath = "loading.gif";

    public LoadingLayout(Context context) {
        super(context);
        this.context = context;
        setUpPartProcessLayout();
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
            if (a != null) {
                loadinglayoutId = a.getResourceId(R.styleable.LoadingLayout_loading_layout, R.layout.loading_layout);
                errorLayoutId = a.getResourceId(R.styleable.LoadingLayout_error_layout_net_fail, R.layout.error_layout_net_fail);
                nodataLayoutId = a.getResourceId(R.styleable.LoadingLayout_error_layout_no_data_fail, R.layout.error_layout_no_data);
            }
        }
        setUpPartProcessLayout();
    }

    /**
     * 设置重试按钮的点击监听
     *
     * @param listener
     */
    public void setRefreshClickListener(OnClickListener listener) {
        this.mRefreshClickListener = listener;
    }

    /**
     * 功能描述:创建局部刷新布局&错误布局
     */
    protected void setUpPartProcessLayout() {
        LayoutInflater infalter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loadingLayout = infalter.inflate(loadinglayoutId, null);
        SimpleDraweeView mCenterLoadAnim = (SimpleDraweeView) loadingLayout.findViewById(R.id.progressbar);
        addView(loadingLayout, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(FrescoImageUtil.getUriFromAsset(LoadingGifFilePath))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        mCenterLoadAnim.setController(controller);
    }

    /**
     * 显示加载中转圈
     */

    public void showProcess() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingLayout.bringToFront();
        }
        if (errorLayout != null) {
            errorLayout.setVisibility(View.GONE);
        }
        if (nodataLayout != null) {
            nodataLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏加载中的错误
     */
    public void removeErrorLayout() {
        if (errorLayout != null) {
            errorLayout.setVisibility(View.GONE);
        }
    }


    /**
     * 隐藏加载中的转圈显示
     */
    public void removeProcess() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载失败后的错误信息
     */
    public void showError() {
        if (errorLayout == null) {
            LayoutInflater infalter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            errorLayout = infalter.inflate(errorLayoutId, null);
            errorTextView = (TextView) errorLayout.findViewById(R.id.error_text);
            if (mRefreshClickListener != null) {
                View errorBtn = errorLayout.findViewById(R.id.error_btn);
                if (errorBtn != null) {
                    errorBtn.setOnClickListener(mRefreshClickListener);
                }
            }
            addView(errorLayout, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        errorLayout.setVisibility(View.VISIBLE);
        errorLayout.bringToFront();
    }

    /**
     * 显示无数据时的错误信息
     */
    public void showNoDataError() {
        if (nodataLayout == null) {
            LayoutInflater infalter = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            nodataLayout = infalter.inflate(nodataLayoutId, null);
            if (mRefreshClickListener != null) {
                View errorBtn = nodataLayout.findViewById(R.id.error_btn);
                if (errorBtn != null) {
                    errorBtn.setOnClickListener(mRefreshClickListener);
                }
            }
            addView(nodataLayout, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER));
        }
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.GONE);
        }
        nodataLayout.setVisibility(View.VISIBLE);
        nodataLayout.bringToFront();
    }

    /**
     * 更新错误提示信息
     *
     * @param str
     */
    public void updateErrorText(String str) {
        if (errorTextView != null) {
            errorTextView.setText(str);
        }
    }


    @Override
    public void onLoadingStart() {
        showProcess();
    }

    @Override
    public void onLoadingFinish() {
        removeProcess();
    }

    @Override
    public void onLoadFail(int code, String msg) {
        removeProcess();
        showError();
    }
}
