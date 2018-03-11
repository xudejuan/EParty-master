package com.example.liu.eparty.activity.statistics;

import android.content.Intent;

import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Tree;
import com.example.liu.eparty.bean.TreeNode;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SingleNodeViewBinder;
import com.example.liu.eparty.util.TreeUtil;

import java.util.List;

public class SingleChooseOrganizationActivity extends BaseActivity
        implements SingleNodeViewBinder.OnChooseListener{

    @Override
    protected String setTitle() {
        return "选择组织";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_single_choose_organization;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void requestData(int page) {
        MyOkHttpUtil.get("tree/recursiveTree")
                .addParams("treeId", String.valueOf(MyApplication.getInstance().getUser().getTreeId()))
                .build()
                .execute(new DataCallback<Tree>(this) {
                    @Override
                    public void showData(List<Tree> mDatas) {
                        initTree(mDatas);
                    }
                });
    }

    private void initTree(List<Tree> mDatas) {
        TreeUtil.initSingleTree(mDatas.get(0), this, R.id.container, this);
    }

    @Override
    public void onChoose(TreeNode treeNode) {
        Intent intent = new Intent();
        intent.putExtra("organizationId", treeNode.getId());
        intent.putExtra("organizationName", treeNode.getValue().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
