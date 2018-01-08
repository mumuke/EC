package com.cuikejia.latte.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.cuikejia.latte.delegates.LatteDelegate;
import com.cuikejia.latte.ec.R;
import com.cuikejia.latte.ec.R2;
import com.cuikejia.latte.net.RestClient;
import com.cuikejia.latte.net.callback.IError;
import com.cuikejia.latte.net.callback.IFailure;
import com.cuikejia.latte.net.callback.ISuccess;
import com.cuikejia.latte.utils.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cuikejia on 2017/12/29.
 */

public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_pwd)
    TextInputEditText mPwd = null;
    @BindView(R2.id.edit_sign_up_re_pwd)
    TextInputEditText mRePwd = null;

    //已有账号，登录
    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        start(new SignInDelegate());
    }

    //注册
    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://127.0.0.1:8080/ckj/json.json")
                    .params("name",mName.getText().toString())
                    .params("email", mEmail.getText().toString())
                    .params("phone",mPhone.getText().toString())
                    .params("password",mPwd.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignUp(response);
                        }
                    })
                    .error(new IError() {
                        @Override
                        public void onError(int code, String msg) {
                            LatteLogger.json("USER_PROFILE", msg);
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            LatteLogger.json("USER_PROFILE","fdsf");
                        }
                    })
                    .build()
                    .post();
            Toast.makeText(getContext(), "注册", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkForm() {
        String name = mName.getText().toString();
        String email = mEmail.getText().toString();
        String phone = mPhone.getText().toString();
        String pwd = mPwd.getText().toString();
        String rePwd = mRePwd.getText().toString();

        boolean isPass = true;

        if (TextUtils.isEmpty(name)) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        //校验邮箱!Patterns.EMAIL_ADDRESS.matcher(email).matches()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        //校验手机号
        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (pwd.isEmpty() || pwd.length() < 6) {
            mPwd.setError("请填写至少6位密码");
            isPass = false;
        } else {
            mPwd.setError(null);
        }

        if (rePwd.isEmpty() || rePwd.length() < 6 || !(rePwd.equals(pwd))) {
            mRePwd.setError("密码验证错误");
            isPass = false;
        } else {
            mRePwd.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
