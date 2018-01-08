package com.cuikejia.latte.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.cuikejia.latte.R;
import com.cuikejia.latte.utils.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by cuikejia on 2017/12/21.
 */

public class LatteLoader {
    //Load相对于屏幕缩放8
    private static final int LOADER_SIZE_SCALE = 8;
    //Load上下偏移10
    private static final int LOADER_OFFSET_SCALE = 10;
    //Load的数据集，统一装在ArrayList里
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    //默认的loader的样式
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        int deviceWidth = DimenUtil.setScreenWidth();
        int deviceHeight = DimenUtil.setScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    //显示默认的load
    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    //显示load，传入枚举类型的load
    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    //关闭load
    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    //回调onCancel， dismiss就是纯粹消失
                    dialog.cancel();
                }
            }
        }
    }
}
