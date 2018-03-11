package com.example.liu.eparty.activity.task;

import android.content.Intent;
import android.util.SparseArray;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Tree;
import com.example.liu.eparty.bean.TreeNode;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.MultiNodeViewBinder;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class MultiChooseOrganizationActivity extends BaseActivity
        implements MultiNodeViewBinder.OnChooseListener {

    private SparseArray<String> sparseArray = new SparseArray<>();

    @Override
    protected String setTitle() {
        return "选择组织";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_multi_choose_organization;
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

    @OnClick(R.id.multi_choose_organization_selected)
    public void showSelected() {
        ArrayList<String> memberNames = new ArrayList<>();
        if (sparseArray.size() == 0) {
            new MaterialDialog.Builder(this)
                    .title("提示")
                    .widgetColor(getResources().getColor(R.color.blue_deep))
                    .content("您目前还未选择")
                    .show();
        } else {
            for (int i = 0; i < sparseArray.size(); i++) {
                memberNames.add(sparseArray.valueAt(i));
            }
            new MaterialDialog.Builder(this)
                    .title("已选择的成员")
                    .widgetColor(getResources().getColor(R.color.blue_deep))
                    .items(memberNames)
                    .show();
        }
    }

    @OnClick(R.id.multi_choose_organization_query)
    public void confirm() {
        ArrayList<Integer> organizationIds = new ArrayList<>();
        ArrayList<String> organizationNames = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            organizationIds.add(sparseArray.keyAt(i));
            organizationNames.add(sparseArray.valueAt(i));
        }
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra("organizationIds", organizationIds);
        intent.putStringArrayListExtra("organizationNames", organizationNames);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void initTree(List<Tree> mDatas) {
        TreeUtil.initMultiTree(mDatas.get(0), this, R.id.container, this);
    }

    @Override
    public void onChoose(TreeNode treeNode, boolean selected) {
        if (selected) {
            sparseArray.put(treeNode.getId(), treeNode.getValue().toString());
        } else {
            sparseArray.delete(treeNode.getId());
        }
    }
}
