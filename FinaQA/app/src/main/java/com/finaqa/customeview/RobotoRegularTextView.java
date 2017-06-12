package com.finaqa.customeview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shrinivas on 12-06-2017.
 */
public class RobotoRegularTextView extends TextView {
    public RobotoRegularTextView(Context context) {
        super(context);
        init();
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Roboto-Regular.ttf");
        setTypeface(tf);

    }
}
