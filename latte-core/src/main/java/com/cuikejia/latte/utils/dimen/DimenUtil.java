package com.cuikejia.latte.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.cuikejia.latte.app.Latte;

/**
 * Created by cuikejia on 2017/12/21.
 */

public class DimenUtil {

    //设置屏幕宽度
    public static int setScreenWidth() {
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    //设置屏幕高度
    public static int setScreenHeight() {
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }


}
