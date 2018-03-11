package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.liu.eparty.R;
import com.example.liu.eparty.base.BaseActivity;
import com.example.liu.eparty.bean.Meeting;
import com.example.liu.eparty.callback.OperateCallback;
import com.example.liu.eparty.util.ACache;
import com.example.liu.eparty.util.DateUtil;
import com.example.liu.eparty.util.MyOkHttpUtil;
import com.example.liu.eparty.util.TipDialogUtil;
import com.example.liu.eparty.util.ToastUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.OnClick;

public class MeetingOperateActivity extends BaseActivity {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double currentLongitude;
    private double currentLatitude;
    private Meeting meeting;
    private String link;

    private TipDialogUtil tipDialogUtil;

    @Override
    protected String setTitle() {
        return "操作";
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_meeting_operate;
    }

    @Override
    protected void init() {
        meeting = (Meeting) ACache.get(this, "meeting").getAsObject("meeting");
    }

    @OnClick(R.id.operate_detail)
    public void showDetail(){
        startActivity(new Intent(this, ShowMeetingDetailActivity.class));
    }

    @OnClick(R.id.operate_sign_in)
    public void signIn(){
        //检查是否为发布者或与发布者同个组织的管理员
        if (meeting.getOrgAdminId() == user.getTreeId() && user.getStattus().equals("管理员")){
            startActivity(new Intent(this, QrcodeActivity.class));
        }else {
            scanQrcode();
        }
    }

    private void scanQrcode() {
        Intent openCameraIntent = new Intent(MeetingOperateActivity.this, CaptureActivity.class);
        startActivityForResult(openCameraIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            link = bundle.getString("result");
            signInByUuid();
        }
    }
    private void signInByUuid() {
        tipDialogUtil = new TipDialogUtil();
        tipDialogUtil.showLoading(this, "正在定位...");
        initLocation();
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

//    @OnClick(R.id.operate_video_monitor)
//    public void monitor(){
        //判断谁可以监督
//        RongCallSession profile = RongCallClient.getInstance().getCallSession();
//        if (profile != null && profile.getActiveTime() > 0) {
//            Toast.makeText(this,
//                    profile.getMediaType() == RongCallCommon.CallMediaType.AUDIO ?
//                            "正在音频通话中" :
//                            "正在视频通话中",
//                    Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
//            Toast.makeText(this, getString(io.rong.callkit.R.string.rc_voip_call_network_error), Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Intent intent = new Intent(RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEAUDIO);
//        intent.putExtra("conversationType", Conversation.ConversationType.PRIVATE.getName().toLowerCase(Locale.US));
//        intent.putExtra("targetId", 1);
//        intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setPackage(getPackageName());
//        getApplicationContext().startActivity(intent);
//    }

    @OnClick(R.id.operate_upload_record)
    public void upload(){
        startActivity(new Intent(this, UploadRecordActivity.class));
    }

    @OnClick(R.id.operate_check_record)
    public void check(){
        startActivity(new Intent(this, CheckRecordActivity.class));
    }

    @OnClick(R.id.operate_comment)
    public void comment(){
        startActivity(new Intent(this, CommentActivity.class));
    }

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                        currentLongitude = aMapLocation.getLongitude();
                        currentLatitude = aMapLocation.getLatitude();
                        tipDialogUtil.dismiss();
                        mLocationClient.unRegisterLocationListener(mLocationListener);
                        mLocationClient.onDestroy();
                        signInWithLocation();
                } else {
                    ToastUtil.show(MeetingOperateActivity.this, "网络错误");
                }
            }
        }

    }

    private void signInWithLocation() {
        MyOkHttpUtil.post("")
                .addParams("userId", String.valueOf(user.getUserId()))
                .addParams("link", link)
                .addParams("time", DateUtil.getTimeWithSecond())
                .addParams("longitude", String.valueOf(currentLongitude))
                .addParams("latitude", String.valueOf(currentLatitude))
                .build()
                .execute(new OperateCallback(this, "正在签到..."));
    }
}
