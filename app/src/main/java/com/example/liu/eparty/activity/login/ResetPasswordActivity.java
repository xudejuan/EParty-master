package com.example.liu.eparty.activity.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @BindView(R.id.reset_password_login_password)
    EditText loginPassword;
    @BindView(R.id.reset_password_query_password)
    EditText queryPassword;

    private int userId;

    @Override
    protected String setTitle() {
        return "重置密码";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void init() {
        userId = getIntent().getIntExtra("userId", 0);
    }

    @OnClick(R.id.reset_password_query)
    public void query(){
        if (check()) {
            MyOkHttpUtil.post("Flow/changPassword")
                    .addParams("userId", String.valueOf(userId))
                    .addParams("userId", String.valueOf(user.getUserId()))
                    .addParams("password", queryPassword.getText().toString().trim())
                    .build()
                    .execute(new OperateCallback(this, "正在修改..."){
                        @Override
                        public void onCompleted() {
                            startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
        }
    }

    private boolean check(){
        if (TextUtils.isEmpty(loginPassword.getText().toString().trim())){
            ToastUtil.show(this, "登录密码不能为空");
            return false;
        }
        if (loginPassword.getText().toString().equals(queryPassword.getText().toString().trim())){
            ToastUtil.show(this, "两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
