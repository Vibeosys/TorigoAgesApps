package com.groceryfood.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by akshay on 16-12-2016.
 */
public class Nunito extends TextView {
    public Nunito(Context context) {
        super(context);
    }

    public Nunito(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/nunito-regular.ttf"));
    }

    public Nunito(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/nunito-regular.ttf"));
    }
}
