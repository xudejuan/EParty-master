package com.example.liu.eparty.activity.login;

import android.text.TextUtils;
import android.widget.EditText;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.register_name)
    EditText name;
    @BindView(R.id.register_authentication)
    EditText authentication;
    @BindView(R.id.register_phone)
    EditText phone;
    @BindView(R.id.register_login_password)
    EditText loginPassword;
    @BindView(R.id.register_query_password)
    EditText queryPassword;

    @Override
    protected String setTitle() {
        return "注册";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.register_register)
    public void register(){
        if (check()) {
            MyOkHttpUtil.post("Flow/Insert")
                    .addParams("flowUserName", name.getText().toString().trim())
                    .addParams("information", authentication.getText().toString().trim())
                    .addParams("tel", phone.getText().toString().trim())
                    .addParams("password", queryPassword.getText().toString().trim())
                    .build()
                    .execute(new OperateCallback(this, "正在注册..."){
                        @Override
                        public void onCompleted() {
                            ToastUtil.show(RegisterActivity.this, "注册成功");
                            finish();
                        }
                    });
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(name.getText().toString().trim())){
            ToastUtil.show(this, "姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(authentication.getText().toString().trim())){
            ToastUtil.show(this, "身份证号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(phone.getText().toString().trim())){
            ToastUtil.show(this, "手机号码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(loginPassword.getText().toString().trim())){
            ToastUtil.show(this, "登录密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(queryPassword.getText().toString().trim())){
            ToastUtil.show(this, "确认密码不能为空");
            return false;
        }
        if (loginPassword.getText().toString().trim().equals(queryPassword.getText().toString().trim())){
            ToastUtil.show(this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
