package com.example.liu.eparty.activity.task;

import android.content.Intent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Tree;
import com.example.liu.eparty.bean.TreeNode;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.SerializableSparseArray;
import com.example.liu.eparty.util.SingleNodeViewBinder;
import com.example.liu.eparty.util.TreeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class ChooseMemberActivity extends BaseActivity
        implements SingleNodeViewBinder.OnChooseListener{

    private SerializableSparseArray<String> sparseArray = new SerializableSparseArray<>();

    @Override
    protected String setTitle() {
        return "选择成员";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_choose_member;
    }

    @Override
    protected void init() {
        ACache.get(this, "sparseArray").put("sparseArray", sparseArray);
    }

    protected void requestData() {
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

    @OnClick(R.id.choose_member_selected)
    public void showSelected() {
        //noinspection unchecked
        sparseArray = (SerializableSparseArray<String>) ACache.get(this, "sparseArray")
                .getAsObject("sparseArray");
        ArrayList<String> memberNames = new ArrayList<>();
        if (sparseArray.size() == 0) {
            new MaterialDialog.Builder(this)
                    .title("提示")
                    .content("您目前还未选择")
                    .show();
        } else {
            for (int i = 0; i < sparseArray.size(); i++) {
                memberNames.add(sparseArray.valueAt(i));
            }
            new MaterialDialog.Builder(this)
                    .title("已选择的成员")
                    .items(memberNames)
                    .show();
        }
    }

    @OnClick(R.id.choose_member_query)
    public void confirm() {
        //noinspection unchecked
        sparseArray = (SerializableSparseArray<String>) ACache.get(this, "sparseArray")
                .getAsObject("sparseArray");
        Intent intent = new Intent();
        ArrayList<Integer> selectedMemberIds = new ArrayList<>();
        ArrayList<String> selectedMemberNames = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            selectedMemberIds.add(sparseArray.keyAt(i));
            selectedMemberNames.add(sparseArray.valueAt(i));
        }
        intent.putIntegerArrayListExtra("memberIds", selectedMemberIds);
        intent.putStringArrayListExtra("memberNames", selectedMemberNames);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void initTree(List<Tree> mDatas) {
        TreeUtil.initSingleTree(mDatas.get(0), this, R.id.container, this);
    }

    @Override
    public void onChoose(TreeNode treeNode) {
        startActivity(new Intent(this, SelectMemberActivity.class));
    }
}
