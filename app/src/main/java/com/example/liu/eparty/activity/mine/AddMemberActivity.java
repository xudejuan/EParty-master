package com.example.liu.eparty.activity.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddMemberActivity extends BaseActivity {

    @BindView(R.id.add_member_name)
    EditText name;
    @BindView(R.id.add_member_phone)
    EditText phone;
    @BindView(R.id.add_member_identity)
    TextView identity;

    @Override
    protected String setTitle() {
        return "添加成员";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.add_member_identity)
    public void chooseIdentity(){
        final List<String> items = new ArrayList<>();
        items.add("书记");
        items.add("管理员");
        items.add("普通党员");
        items.add("流动党员管理员");
        new MaterialDialog.Builder(this)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        identity.setText(items.get(position));
                    }
                }).show();
    }

    @OnClick(R.id.add_member_add)
    public void addMember(){
        if (check()){
            MyOkHttpUtil.post("")
                    .addParams("userId", String.valueOf(user.getUserId()))
                    .addParams("userId", String.valueOf(user.getUserId()))
                    .addParams("userId", String.valueOf(user.getUserId()))
                    .addParams("userId", String.valueOf(user.getUserId()))
                    .build()
                    .execute(new OperateCallback(this, "正在添加..."){
                        @Override
                        public void onCompleted() {
                            setResult(RESULT_OK);
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
        if (TextUtils.isEmpty(phone.getText().toString().trim())){
            ToastUtil.show(this, "手机号码不能为空");
            return false;
        }
        if (identity.getText().toString().trim().equals(getResources().getString(R.string.click_to_choose_identity))){
            ToastUtil.show(this, "请选择身份");
            return false;
        }
        return true;
    }
}
