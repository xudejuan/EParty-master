package com.example.liu.eparty.activity.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.User;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity {

    @BindView(R.id.authentication_name)
    EditText name;
    @BindView(R.id.authentication_authentication)
    EditText authentication;
    @BindView(R.id.authentication_phone)
    EditText phone;

    @Override
    protected String setTitle() {
        return "身份验证";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.authentication_next)
    public void next() {
        if (check()) {
            MyOkHttpUtil.post("Flow/Identification")
                    .addParams("flowUserName", name.getText().toString().trim())
                    .addParams("information", authentication.getText().toString().trim())
                    .addParams("tel", phone.getText().toString().trim())
                    .build()
                    .execute(new DataCallback<User>(this) {
                        @Override
                        public void showData(List<User> mDatas) {
                            startActivity(new Intent(AuthenticationActivity.this, ResetPasswordActivity.class)
                                    .putExtra("userId", mDatas.get(0).getUserId()));
                        }
                    });
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            ToastUtil.show(this, "姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(authentication.getText().toString().trim())) {
            ToastUtil.show(this, "身份证号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(phone.getText().toString().trim())) {
            ToastUtil.show(this, "手机号码不能为空");
            return false;
        }
        return true;
    }
}
