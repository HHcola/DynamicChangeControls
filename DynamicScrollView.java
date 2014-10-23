package com.example.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class DynamicScrollView extends ScrollView {

    private static String TAG = DynamicScrollView.class.getSimpleName();

    private OnScrollListener scrollListener;
    public DynamicScrollView(Context context) {
        super(context);
    }

    public DynamicScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.scrollListener = listener;
    }
    
    //
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scrollListener != null) {
            scrollListener.onScroll(getScrollY());
        }
    }

    public static interface OnScrollListener {
        void onScroll(int scrollY);
    }
}
