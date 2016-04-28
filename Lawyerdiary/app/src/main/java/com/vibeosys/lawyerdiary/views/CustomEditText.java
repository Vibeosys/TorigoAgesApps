package com.vibeosys.lawyerdiary.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by shrinivas on 28-04-2016.
 */
public class CustomEditText extends EditText {
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
    }
}
