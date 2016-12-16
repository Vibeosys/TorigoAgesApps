package com.groceryfood.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.groceryfood.R;

/**
 * Created by akshay on 16-12-2016.
 */
public class Utils2 {

    static BadgeDrawable badge;

    public Utils2(Context context, LayerDrawable icon) {
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public static void setBadgeCount(int count) {
        // Reuse drawable if possible
        badge.setCount(count);
    }
}
