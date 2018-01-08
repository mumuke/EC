package com.cuikejia.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.cuikejia.latte.app.Latte;
import com.cuikejia.latte.net.callback.IResquest;
import com.cuikejia.latte.net.callback.ISuccess;
import com.cuikejia.latte.utils.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by cuikejia on 2017/12/22.
 * 处理下载任务，边读取边写入
 * <p>
 * AsyncTask<Object,Void,File>
 * Object指的是传入类型
 * Void指的是进度
 * File指的是返回类型
 */

public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final IResquest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IResquest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String) objects[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.isEmpty()) {
            downloadDir = "down_loads";
        }

        if (extension == null || extension.isEmpty()) {
            extension = "";
        }

        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name);
        }
    }

    //执行完异步回到主线程的操作
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    //安装APK
    private void autoInstallApk(File file) {
        //判断文件名的后缀
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //用于显示用户的数据。比较通用，会根据用户的数据类型打开相应的Activity。
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplication().startActivity(install);
        }
    }
}
