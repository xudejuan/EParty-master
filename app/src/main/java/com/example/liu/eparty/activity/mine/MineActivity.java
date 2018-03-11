package com.example.liu.eparty.activity.mine;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.login.LoginActivity;
import com.example.liu.eparty.activity.login.ResetPasswordActivity;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.CleanCacheUtil;
import com.example.liu.eparty.util.FileUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class MineActivity extends BaseActivity {

    @BindView(R.id.mine_name)
    TextView name;
    @BindView(R.id.mine_phone)
    TextView phone;
    @BindView(R.id.mine_identity)
    TextView identity;
    @BindView(R.id.mine_secretary)
    TextView secretary;
    @BindView(R.id.mine_administrator)
    TextView administrator;
    @BindView(R.id.mine_organization)
    TextView organization;
    @BindView(R.id.mine_cache_size)
    TextView cacheSize;

    @Override
    protected String setTitle() {
        return "我的";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void init() {
        name.setText(user.getUserName());
        phone.setText(user.getTels());
        identity.setText(user.getStattus());
        secretary.setText(user.getStattus());
        administrator.setText(user.getStattus());
        organization.setText(user.getTreeName());
    }

    @OnClick(R.id.mine_organization_member)
    public void showMember() {
        Intent intent = new Intent(this, MemberActivity.class);
        intent.putExtra("treeId", user.getTreeId());
        startActivity(intent);
    }

    @OnClick(R.id.mine_reset_password)
    public void resetPassword() {
        Intent intent = new Intent(this, ResetPasswordActivity.class)
                .putExtra("userId", user.getUserId());
        startActivity(intent);
    }

    @OnClick(R.id.mine_clean)
    public void cleanCache() {
        showDialog();
    }

    private void showDialog() {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认清除已下载的文件，图片和视频？")
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            clean();
                        }
                        dialog.dismiss();
                    }
                }).show();

    }

    private void clean() {
        CleanCacheUtil.cleanCustomCache(FileUtil.getSavePath(this));
        showCache();
    }

    private void showCache() {
        try {
            cacheSize.setText(CleanCacheUtil.getCacheSize(new File(FileUtil.getSavePath(this))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.mine_logout)
    public void logout() {
        MyOkHttpUtil.post("")
                .build()
                .execute(new OperateCallback(this, "退出中..."){
                    @Override
                    public void onCompleted() {
                        Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
