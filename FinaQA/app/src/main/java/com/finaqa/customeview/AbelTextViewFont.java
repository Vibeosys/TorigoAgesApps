package com.finaqa.customeview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shrinivas on 05-05-2017.
 */
public class AbelTextViewFont extends TextView {
    public AbelTextViewFont(Context context) {
        super(context);
        init();
    }

    public AbelTextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbelTextViewFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Abel-Regular.ttf");
        setTypeface(tf);

    }
}
