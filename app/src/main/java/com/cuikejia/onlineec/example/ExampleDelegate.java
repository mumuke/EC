package com.cuikejia.onlineec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.cuikejia.latte.delegates.LatteDelegate;
import com.cuikejia.latte.net.RestClient;
import com.cuikejia.latte.net.callback.IError;
import com.cuikejia.latte.net.callback.IFailure;
import com.cuikejia.latte.net.callback.ISuccess;
import com.cuikejia.latte.ui.loader.LoaderStyle;
import com.cuikejia.latte.utils.log.LatteLogger;

/**
 * Created by cuikejia on 2017/12/19.
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        test();
    }

    public void test() {
        RestClient.builder()
                .url("http://127.0.0.1/index")
                .loader(getContext(), LoaderStyle.BallClipRotateMultipleIndicator)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LatteLogger.e("ExampleDelegate",response);
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getContext(), code, Toast.LENGTH_LONG).show();
            }
        }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "111", Toast.LENGTH_LONG).show();
            }
        }).build().get();
    }

}
