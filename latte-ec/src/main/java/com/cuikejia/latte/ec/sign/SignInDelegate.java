package com.cuikejia.latte.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.cuikejia.latte.delegates.LatteDelegate;
import com.cuikejia.latte.ec.R;
import com.cuikejia.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cuikejia on 2018/1/3.
 */

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_pwd)
    TextInputEditText mPwd = null;

    //注册
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn() {
        if (checkForm()) {
            Toast.makeText(getContext(), "登录", Toast.LENGTH_LONG).show();
        }
    }

    //微信登录
    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWechat() {

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink() {
        start(new SignUpDelegate());
    }

    private boolean checkForm() {
        String email = mEmail.getText().toString();
        String pwd = mPwd.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("邮箱格式不对");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (pwd.isEmpty() || pwd.length() < 6) {
            mPwd.setError("请填写至少6位密码");
            isPass = false;
        } else {
            mPwd.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
