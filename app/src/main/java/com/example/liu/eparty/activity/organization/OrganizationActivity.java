package com.example.liu.eparty.activity.organization;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.R;
import com.example.liu.eparty.activity.mine.MemberActivity;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Tree;
import com.example.liu.eparty.bean.TreeNode;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.ShowNodeViewBinder;
import com.example.liu.eparty.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class OrganizationActivity extends BaseActivity
        implements ShowNodeViewBinder.OnOperateListener{

    @Override
    protected String setTitle() {
        return "组织";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_organization;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.get("tree/recursiveTree")
                .addParams("treeId", String.valueOf(user.getTreeId()))
                .build()
                .execute(new DataCallback<Tree>(this){
                    @Override
                    public void showData(List<Tree> mDatas) {
                        initTree(mDatas);
                    }
                });
    }

    private void initTree(List<Tree> mDatas) {
        TreeUtil.initShowTree(mDatas.get(0), this, R.id.container, this);
    }

    @Override
    public void showDialog(final TreeNode treeNode) {
        List<String> items = new ArrayList<>();
        items.add("查看该组织成员");
        items.add("在该组织下添加组织");
        items.add("修改该组织");
        items.add("删除该组织");
        new MaterialDialog.Builder(this)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position){
                            case 0:
                                startActivity(new Intent(OrganizationActivity.this,
                                        MemberActivity.class).putExtra("treeId", treeNode.getId()));
                                break;
                            case 1:
                                addOrganization(treeNode.getId());
                                break;
                            case 2:
                                updateOrganization(treeNode.getId());
                                break;
                            case 3:
                                deleteOrganization(treeNode.getId());
                                break;
                        }
                    }
                });
    }

    private void addOrganization(final int treeId) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .input("请输入组织名称", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.length() == 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                    }
                })
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .positiveText("确定")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE){
                            add(treeId, dialog.getInputEditText().getText().toString());
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    private void add(int treeId, String organizationName) {
        MyOkHttpUtil.post("tree/add")
                .addParams("pid", String.valueOf(treeId))
                .addParams("text", organizationName)
                .build()
                .execute(new OperateCallback(this, "正在添加..."){
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    private void updateOrganization(final int treeId) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .widgetColor(getResources().getColor(R.color.blue_deep))
                .input("请输入组织名称", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (input.length() == 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                    }
                })
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .positiveText("确定")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE){
                            update(treeId, dialog.getInputEditText().getText().toString());
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    private void update(int treeId, String organizationName){
        MyOkHttpUtil.post("tree/updated")
                .addParams("id", String.valueOf(treeId))
                .addParams("text", organizationName)
                .build()
                .execute(new OperateCallback(this, "正在修改..."){
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }

    private void deleteOrganization(final int treeId) {
        new MaterialDialog.Builder(this)
                .title("提示")
                .content("确认删除该组组织及其子组织?")
                .positiveText("确定")
                .positiveColor(getResources().getColor(R.color.blue_deep))
                .negativeText("取消")
                .negativeColor(getResources().getColor(R.color.blue_deep))
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            delete(treeId);
                        }
                    }
                }).show();
    }

    private void delete(int treeId){
        MyOkHttpUtil.post("tree/delete")
                .addParams("cid", String.valueOf(treeId))
                .build()
                .execute(new OperateCallback(this, "正在删除..."){
                    @Override
                    public void onCompleted() {
                        requestData(1);
                    }
                });
    }
}
