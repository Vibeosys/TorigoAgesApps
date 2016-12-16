package com.groceryfood.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by akshay on 16-12-2016.
 */
public class OpenSans extends TextView {
    public OpenSans(Context context) {
        super(context);
    }

    public OpenSans(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/opensans_light.ttf"));
    }

    public OpenSans(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/opensans_light.ttf"));
    }
}
