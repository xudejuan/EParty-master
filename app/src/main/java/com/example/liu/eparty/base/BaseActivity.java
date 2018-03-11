package com.example.liu.eparty.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.eparty.MyApplication;
import com.example.liu.eparty.R;
import com.example.liu.eparty.bean.User;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity封装
 */

public abstract class BaseActivity extends AutoLayoutActivity {

    protected User user;
    protected String token;
    private Unbinder mUnbinder;
    private View rootView;

    private FinishBroadcast finishBroadcast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(this, R.layout.activity_title, null);
        addContent();
        initUserAndToken();
        initBroadcast();
        init();
        requestData(1);
    }

    private void addContent() {
        TextView title = rootView.findViewById(R.id.title);
        ImageView back = rootView.findViewById(R.id.back);
        title.setText(setTitle());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FrameLayout flContent = rootView.findViewById(R.id.fl_content);
        View content = View.inflate(this, setLayoutId(), null);
        if (content != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            flContent.addView(content, params);
            mUnbinder = ButterKnife.bind(this, rootView);
        }
        setContentView(rootView);
    }

    private void initUserAndToken() {
        user = MyApplication.getInstance().getUser();
        token = MyApplication.getInstance().getToken();
    }

    private void initBroadcast() {
        finishBroadcast = new FinishBroadcast();
        IntentFilter intentFilter = new IntentFilter("com.example.liu.baseActivity");
        registerReceiver(finishBroadcast, intentFilter);
    }

    protected abstract String setTitle();

    @LayoutRes
    protected abstract int setLayoutId();

    protected abstract void init();

    protected void requestData(int page) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        unregisterReceiver(finishBroadcast);
    }


    public class FinishBroadcast extends BroadcastReceiver {

        public void onReceive(Context arg0, Intent intent) {
            int closeAll = intent.getIntExtra("closeAll", 0);
            if (closeAll == 1) {
                finish();
            }
        }
    }
}
