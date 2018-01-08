package com.cuikejia.latte.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.cuikejia.latte.delegates.LatteDelegate;
import com.cuikejia.latte.ec.R;
import com.cuikejia.latte.ec.R2;
import com.cuikejia.latte.utils.storage.LattePreference;
import com.cuikejia.latte.utils.timer.BaseTimerTask;
import com.cuikejia.latte.utils.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cuikejia on 2017/12/26.
 * 首页倒计时页面
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTVLauncherTimer = null;

    private Timer mTimer = null;
    //倒计时时间
    private int mCount = 5;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mCount < 0) {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
                checkIsShowScroll();
            }
        }
    }

    //初始化计时器
    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        mTimer.schedule(baseTimerTask, 0, 1000);
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    //判断是否出现过滑动启动页
    private void checkIsShowScroll() {
        boolean isShowScroll = LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name());
        if (!isShowScroll) {
            //singleTask:在栈中有这个activity就直接到栈顶，它之上的activity都销毁，然后在onNewIntent()调用
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            //TODO 检查用户是否登录

        }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTimer != null) {
                    //跳过5s
                    mTVLauncherTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
