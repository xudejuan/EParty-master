package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.PlaceAdapter;
import com.example.liu.eparty.bean.Place;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.TipDialogUtil;
import com.example.liu.eparty.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChoosePlaceActivity extends AppCompatActivity implements
        AMap.OnMapClickListener, PoiSearch.OnPoiSearchListener,
        AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener{

    @BindView(R.id.recyclerView_place)
    RecyclerView recyclerView;
    @BindView(R.id.mapView)
    MapView mapView;

    private AMap aMap;
    private PlaceAdapter placeAdapter;
    private List<Place> mDatas = new ArrayList<>();
    private Place place;
    private LatLonPoint latLonPoint;
    private PoiSearch.Query query;

    private LatLng mFinalChoosePosition; //最终选择的点
    private GeocodeSearch geocoderSearch;

    private boolean firstLocation = true;
    private boolean isHandDrag = true;
    private boolean isFirstLoadList = true;
    private boolean isBackFromSearchChoose = false;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private double currentLongitude;
    private double currentLatitude;

    private TipDialogUtil tipDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_place);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
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

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    if (firstLocation) {
                        currentLongitude = aMapLocation.getLongitude();
                        currentLatitude = aMapLocation.getLatitude();
                        tipDialogUtil.dismiss();
                        firstLocation = false;
                        initMapView();
                    }
                } else {
                    ToastUtil.show(ChoosePlaceActivity.this, "网络错误");
                }
            }
        }
    }

    private void initMapView() {
        latLonPoint = new LatLonPoint(currentLatitude, currentLongitude);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter(this, mDatas);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mFinalChoosePosition = convertToLatLng(mDatas.get(position).getLatLonPoint());
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setChoose(false);
                }
                mDatas.get(position).setChoose(true);
                isHandDrag = false;
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mFinalChoosePosition.latitude, mFinalChoosePosition.longitude), 20));
            }
        }));
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapClickListener(this);
            aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
            Marker locationMarker = aMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location)))
                    .position(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude())));
            mFinalChoosePosition = locationMarker.getPosition();
        }
        setup();
        // 只要地图发生改变，就会调用 onCameraChangeFinish ，不是说非要手动拖动屏幕才会调用该方法
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()), 20));
    }

    private void setup() {
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    public void getAddress(final LatLng latLonPoint) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(convertToLatLonPoint(latLonPoint), 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    protected void doSearchQuery() {
        int currentPage = 0;
        query = new PoiSearch.Query("", "", "惠州市");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lpTemp = convertToLatLonPoint(mFinalChoosePosition);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);  // 实现  onPoiSearched  和  onPoiItemSearched
        poiSearch.setBound(new PoiSearch.SearchBound(lpTemp, 5000, true));//
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        mFinalChoosePosition = cameraPosition.target;
        Log.e("111", "拖动地图 Finish changeCenterMarker 经度" + mFinalChoosePosition.longitude + "   纬度：" + mFinalChoosePosition.latitude);
        if (isHandDrag||isFirstLoadList) {
            getAddress(mFinalChoosePosition);
            doSearchQuery();
        } else if(isBackFromSearchChoose){
            doSearchQuery();
        }else{
            placeAdapter.notifyDataSetChanged();
        }
        isHandDrag = true;
        isFirstLoadList = false;
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (rCode == 1000) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                place = new Place(addressName, addressName, true, convertToLatLonPoint(mFinalChoosePosition));
            } else {
                ToastUtil.show(this, "没有结果");
            }
        } else if (rCode == 27) {
            ToastUtil.show(this, "网络错误");
        } else if (rCode == 32) {
            ToastUtil.show(this, "key错误");
        } else {
            ToastUtil.show(this,
                    "未知错误" + rCode);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rcode) {
        if (rcode == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    List<PoiItem> poiItems = poiResult.getPois();
                    mDatas.clear();
                    mDatas.add(place);// 第一个元素
                    Place place;
                    for (PoiItem poiItem : poiItems) {
                        Log.e("111", "得到的数据 poiItem " + poiItem.getSnippet());
                        place = new Place(poiItem.getTitle(), poiItem.getSnippet(), false, poiItem.getLatLonPoint());
                        mDatas.add(place);
                    }
                    if (isHandDrag) {
                        mDatas.get(0).setChoose(true);
                    }
                    placeAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtil
                        .show(this, "对不起，没有搜索到相关数据");
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @OnClick(R.id.choose_place_search)
    public void search(){
                Intent intent = new Intent(this, SearchAddressActivity.class);
                intent.putExtra("point", mFinalChoosePosition);
                startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Place backEntity = data.getParcelableExtra("place");
            place = backEntity; // 上一个页面传过来的 item对象
            place.setChoose(true);
            isBackFromSearchChoose = true;
            isHandDrag = false;
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(backEntity.getLatLonPoint().getLatitude(),
                            backEntity.getLatLonPoint().getLongitude()), 20));
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.unRegisterLocationListener(mLocationListener);
        mLocationClient.onDestroy();
    }
}
