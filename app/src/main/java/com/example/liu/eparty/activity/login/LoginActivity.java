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
import com.example.liu.eparty.callback.BeanCallback;
import com.example.liu.eparty.util.ConstantUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_phone)
    EditText phone;
    @BindView(R.id.login_password)
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_login)
    public void login() {
        if (check()) {
            OkHttpUtils.get()
                    .url(ConstantUtil.BASE_URL + "kjgchjfk")
                    .addParams("identify", ConstantUtil.IDENTIFY)
                    .addParams("phone", phone.getText().toString())
                    .addParams("password", password.getText().toString())
                    .build()
                    .execute(new BeanCallback<User>(this){
                        @Override
                        public void showData(User data) {
                            MyApplication.getInstance().setToken(data.getToken());
                            MyApplication.getInstance().setUser(data);
                            ToastUtil.show(LoginActivity.this, "登陆成功");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
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

    @OnClick(R.id.login_flow_login)
    public void flowLogin() {
        startActivity(new Intent(this, FlowLoginActivity.class));
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
