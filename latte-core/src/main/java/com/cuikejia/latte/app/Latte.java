package com.cuikejia.latte.app;

import android.content.Context;

/**
 * Created by cuikejia on 2017/12/18.
 */

public final class Latte {


    public static Configuator init(Context context) {
        //配置
        Configuator.getInstance().getLattieConfigs().put(ConfigType.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configuator.getInstance();
    }

    public static Configuator getConfiguator(){
        return Configuator.getInstance();
    }

    public static <T> T getConfiguration(Object object) {
        return getConfiguator().getConfiguration(object);
    }

    public static Context getApplication() {
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

}
