package com.groceryfood.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.groceryfood.R;
import com.groceryfood.helpers.LocaleHelper;

/**
 * Created by akshay on 28-12-2016.
 */
public class ImageViewArabic extends ImageView {
    public ImageViewArabic(Context context) {
        super(context);
    }

    public ImageViewArabic(Context context, AttributeSet attrs) {
        super(context, attrs);
        String language = LocaleHelper.getLanguage(context);
        if (language.equals("en")) {
            this.setImageDrawable(getResources().getDrawable(R.drawable.qadmni_img_logo_english, null));
        } else if (language.equals("ar")) {
            this.setImageDrawable(getResources().getDrawable(R.drawable.qadmni_img_logo_arabic, null));
        }
    }

    public ImageViewArabic(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String language = LocaleHelper.getLanguage(context);
        if (language.equals("en")) {
            this.setImageDrawable(getResources().getDrawable(R.drawable.qadmni_img_logo_english, null));
        } else if (language.equals("ar")) {
            this.setImageDrawable(getResources().getDrawable(R.drawable.qadmni_img_logo_arabic, null));
        }
    }
}
