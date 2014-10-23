package com.example.commonuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.custom.view.DynamicScrollView;
import com.example.custom.view.DynamicScrollView.OnScrollListener;

public class DynamicChangeControlsActivity extends Activity {

    private static String TAG = DynamicChangeControlsActivity.class
            .getSimpleName();

    private View mViewPretend;
    // 头部view
    private LinearLayout mLlAppTop;
    private LinearLayout mLlAppContent;
    private ImageView mIvTopLogo;
    private TextView mTvTopName;
    private TextView mTvTopDownloadNumSize;
    private Button mBtnTopInstall;
    private TextView mTvTopCate;
    private TextView mTvTopVersion;
    private FrameLayout mFlTopLogo;
    private FrameLayout mFlTopInstall;
    private View mViewShadow;
    // 高度
    private float mNameSize;
    private int mLogoSize;
    private int mHeaderHeight;
    private int mInstallHeight;
    private int mInstallWidth;
    private int mInstallTop;
    private int mNameTop;
    private int mNameBottom;
    
    // 内容view
    private DynamicScrollView mSvContent;
    
    private static final float TOP_HEIGHT_PERCENT = 0.8F;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.dynamic_control_view);
        initUI();
    }

    private void initUI() {
        mViewPretend = findViewById(R.id.view_pretend);
        mLlAppTop = (LinearLayout) findViewById(R.id.ll_app_detail_top);
        mLlAppTop.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        mLlAppTop.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        mLlAppTop.measure(0, 0);
                        mHeaderHeight = mLlAppTop.getMeasuredHeight();
                        mNameSize = mTvTopName.getTextSize();
                        mLogoSize = mFlTopLogo.getLayoutParams().height;
                        LayoutParams lp = (LayoutParams) mFlTopInstall
                                .getLayoutParams();
                        mInstallHeight = lp.height;
                        mInstallWidth = lp.width;
                        mInstallTop = lp.topMargin;
                        LayoutParams lpName = (LayoutParams) mTvTopName
                                .getLayoutParams();
                        mNameTop = lpName.topMargin;
                        mNameBottom = lpName.bottomMargin;

                        mViewPretend.getLayoutParams().height = mHeaderHeight;
                        mLlAppTop.requestLayout();
                    }
                });

        // title
        mIvTopLogo = (ImageView) findViewById(R.id.iv_app_detail_logo);
        mTvTopName = (TextView) findViewById(R.id.tv_app_detail_name);
        mTvTopDownloadNumSize = (TextView) findViewById(R.id.tv_app_detail_download_num_size);
        mBtnTopInstall = (Button) findViewById(R.id.btn_app_detail_install);

        mFlTopLogo = (FrameLayout) findViewById(R.id.fl_app_detail_logo);
        mFlTopInstall = (FrameLayout) findViewById(R.id.fl_app_detail_install);

        // content
        mSvContent = (DynamicScrollView) findViewById(R.id.sv_app_detail_content);
        mSvContent.setOnScrollListener(mOnScrollListener);
    }

    private final OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScroll(int scrollY) {
            touchChangeAction(scrollY);
        }
    };

    private void touchChangeAction(int y) {
        Log.d(TAG, "touchChangeAction: scrollY = " + y);
        if (y < 0)  {
            Log.d(TAG, "touchChangeAction y < 0");
            return ;
        }
        int height = mHeaderHeight - y;
        if (height < 0) {
            height = 0;
        }
        Log.d(TAG, "touchChangeAction height = " + height);
        float scale = (height == 0) ? 0 : (float) height / mHeaderHeight;
        Log.d(TAG, "touchChangeAction scale = " + scale);
        
        if (scale >= 0) {
            LayoutParams lp = (LayoutParams) mFlTopInstall.getLayoutParams();
            lp.topMargin = (int) (mInstallTop * scale);
            LayoutParams lpName = (LayoutParams) mTvTopName.getLayoutParams();
            lpName.topMargin = (int) (mNameTop * scale);
            lpName.bottomMargin = (int) (mNameBottom * scale);

            if (scale >= TOP_HEIGHT_PERCENT) {
                mTvTopName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNameSize
                        * scale);
                RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) mFlTopLogo
                        .getLayoutParams();
                Log.d(TAG, "logo size:" + mLogoSize * scale + " scale:"
                        + scale + " height:" + height + " y:" + y);
                layoutParams.height = (int) (mLogoSize * scale);
                layoutParams.width = (int) (mLogoSize * scale);

                lp.height = (int) (mInstallHeight * scale);
                lp.width = (int) (mInstallWidth * scale);
            }
        }
        mLlAppTop.requestLayout();
        mLlAppTop.measure(0, 0);
        mViewPretend.getLayoutParams().height = mLlAppTop.getMeasuredHeight();
        mViewPretend.requestLayout();
    }
}
