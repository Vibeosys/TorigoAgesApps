package com.vibeosys.lawyerdiary.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Vibeosys software on 28-04-2016.
 */

/**
 * Custom edit text with the Roboto font
 */
public class CustomEditText extends EditText {
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
    }
}
