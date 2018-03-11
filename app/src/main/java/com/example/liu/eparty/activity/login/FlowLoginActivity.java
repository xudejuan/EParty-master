package com.example.liu.eparty.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.MainActivity;
import com.example.liu.eparty.bean.User;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.ConstantUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlowLoginActivity extends AppCompatActivity {

    @BindView(R.id.flow_login_phone)
    EditText phone;
    @BindView(R.id.flow_login_password)
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.flow_login_login)
    public void login() {
        if (check()) {
            OkHttpUtils.post()
                    .url(ConstantUtil.BASE_URL + "FlowUserLogin")
                    .addParams("identify", ConstantUtil.IDENTIFY)
                    .addParams("tel", phone.getText().toString().trim())
                    .addParams("password", password.getText().toString().trim())
                    .build()
                    .execute(new DataCallback<User>(this){
                        @Override
                        public void showData(List<User> mDatas) {
                            MyApplication.getInstance().setToken(mDatas.get(0).getToken());
                            MyApplication.getInstance().setUser(mDatas.get(0));
                            ToastUtil.show(FlowLoginActivity.this, "登陆成功");
                            startActivity(new Intent(FlowLoginActivity.this, MainActivity.class));
                            finish();
                        }
                    });
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(phone.getText().toString().trim())) {
            ToastUtil.show(this, "手机号码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            ToastUtil.show(this, "登录密码不能为空");
            return false;
        }
        return true;
    }

    @OnClick(R.id.flow_login_register)
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.flow_login_forget)
    public void forgetPassword() {
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent("com.example.liu.baseActivity");
            intent.putExtra("closeAll", 1);
            sendBroadcast(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
