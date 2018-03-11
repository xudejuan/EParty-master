package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.RecordAdapter;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.bean.Record;
import com.example.liu.eparty.callback.DataCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.MyItemDecoration;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.SmartRefreshLayoutUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

public class CheckRecordActivity extends BaseActivity {

    @BindView(R.id.refreshLayout_check_record)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView_check_record)
    RecyclerView recyclerView;

    private RecordAdapter recordAdapter;

    @Override
    protected String setTitle() {
        return "查看记录";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_check_record;
    }

    @Override
    protected void init() {
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        SmartRefreshLayoutUtil.init(smartRefreshLayout);
        SmartRefreshLayoutUtil.setOnRequestDataListener(new SmartRefreshLayoutUtil.OnRequestDataListener() {
            @Override
            public void requestDatas(int page) {
                requestData(page);
            }
        });
    }

    @Override
    protected void requestData(int page) {
        Meeting meeting = (Meeting) ACache.get(this, "meeting").getAsObject("meeting");
        MyOkHttpUtil.get("")
                .addParams("meetingId", String.valueOf(meeting.getId()))
                .addParams("page", String.valueOf(page))
                .build()
                .execute(new DataCallback<Record>(this){
                    @Override
                    public void showData(List<Record> mDatas) {
                        initRecyclerView(mDatas);
                    }
                });
    }

    public void initRecyclerView(final List<Record> mDatas) {
        switch (SmartRefreshLayoutUtil.state){
            case SmartRefreshLayoutUtil.REFRESH: {
                if (recordAdapter == null) {
                    recordAdapter = new RecordAdapter(this, mDatas);
                    recyclerView.setAdapter(recordAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new MyItemDecoration());
                    recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                            recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(CheckRecordActivity.this, CheckRecordDetailActivity.class);
                            intent.putExtra("record", mDatas.get(position));
                            startActivity(intent);
                        }
                    }));
                }else {
                    recordAdapter.refreshData(mDatas);
                    recyclerView.scrollToPosition(0);
                }
                break;
            }
            case SmartRefreshLayoutUtil.LOAD_MORE: {
                recordAdapter.add(mDatas);
                recyclerView.scrollToPosition(recordAdapter.getData().size());
            }
        }
    }
}
