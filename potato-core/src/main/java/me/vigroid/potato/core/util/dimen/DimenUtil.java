package me.vigroid.potato.core.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import me.vigroid.potato.core.app.Potato;

/**
 * Created by vigroid on 10/3/17.
 */

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources = Potato.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = Potato.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
