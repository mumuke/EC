package com.cuikejia.latte.net.download;

import android.os.AsyncTask;

import com.cuikejia.latte.net.RestCreator;
import com.cuikejia.latte.net.callback.IError;
import com.cuikejia.latte.net.callback.IFailure;
import com.cuikejia.latte.net.callback.IResquest;
import com.cuikejia.latte.net.callback.ISuccess;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cuikejia on 2017/12/22.
 */

public class DownloadHandler {
    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private final IResquest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    //下载文件夹
    private final String DOWNLOAD_DIR;
    //下载后缀名
    private final String EXTENSION;
    //下载文件名
    private final String NAME;

    public DownloadHandler(String url, IResquest request, ISuccess success, IFailure failure, IError error, String downloadDir, String extension, String name) {
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            RestCreator.getRestService().download(URL, PARAMS)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                final ResponseBody responseBody = response.body();
                                SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);

                                //一定要判断，否则文件下载不全
                                if (task.isCancelled()) {
                                    if (REQUEST != null) {
                                        REQUEST.onRequestEnd();
                                    }
                                }
                            } else {
                                if (ERROR != null) {
                                    ERROR.onError(response.code(), response.message());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            if (FAILURE != null) {
                                FAILURE.onFailure();
                            }
                        }
                    });
        }
    }

}
