package com.cuikejia.onlineec.example;

import android.app.Application;

import com.cuikejia.latte.app.Latte;
import com.cuikejia.latte.ec.database.DatabaseManager;
import com.cuikejia.latte.ec.icon.FontEcModule;
import com.cuikejia.latte.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by cuikejia on 2017/12/18.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http:127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configue();

        DatabaseManager.getInstance().init(this);
    }
}
