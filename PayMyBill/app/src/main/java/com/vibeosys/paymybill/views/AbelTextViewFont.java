package com.vibeosys.paymybill.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by shrinivas on 19-10-2016.
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
       Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Abel-Regular.ttf");
       setTypeface(tf);

   }
}
