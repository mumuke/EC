package com.cuikejia.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by cuikejia on 2017/12/18.
 * 所有的配置文件
 * 单例懒汉模式，初始化一次
 */

public class Configuator {
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configuator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY, false);
    }

    //    单例懒汉模式
    public static Configuator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLattieConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        private static final Configuator INSTANCE = new Configuator();
    }

    public final void configue() {
        initIcon();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY, true);
    }

    public final Configuator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    private void initIcon() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    //字体图标
    public final Configuator withIcon(IconFontDescriptor iconFontDescriptor) {
        ICONS.add(iconFontDescriptor);
        return this;
    }

    //拦截器
    public final Configuator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS,INTERCEPTORS);
        return this;
    }

    public final Configuator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS,INTERCEPTORS);
        return this;
    }


    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call config");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }

}
