package com.groceryfood.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.groceryfood.helpers.LocaleHelper;

/**
 * Created by akshay on 16-12-2016.
 */
public class Nunito extends TextView {
    public Nunito(Context context) {
        super(context);
    }

    public Nunito(Context context, AttributeSet attrs) {
        super(context, attrs);
        String language = LocaleHelper.getLanguage(context);
        float textSize = getTextSize();
        if (language.equals("en")) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/nunito-regular.ttf"));
        } else if (language.equals("ar")) {
            setTextSize(convertPixelsToDp(textSize, context) - 3);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/notokufiar-reg.ttf"));
        }
    }

    public Nunito(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String language = LocaleHelper.getLanguage(context);
        float textSize = getTextSize();
        if (language.equals("en")) {
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/nunito-regular.ttf"));
        } else if (language.equals("ar")) {
            setTextSize(convertPixelsToDp(textSize, context) - 3);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/notokufiar-reg.ttf"));
        }
    }

    private float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
