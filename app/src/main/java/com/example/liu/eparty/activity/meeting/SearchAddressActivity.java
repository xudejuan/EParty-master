package com.example.liu.eparty.activity.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.liu.eparty.R;
import com.example.liu.eparty.adapter.PlaceAdapter;
import com.example.liu.eparty.bean.Place;
import com.example.liu.eparty.util.RecyclerViewClickListener;
import com.example.liu.eparty.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchAddressActivity extends AppCompatActivity
        implements PoiSearch.OnPoiSearchListener {

    @BindView(R.id.recyclerView_search_address)
    RecyclerView recyclerView;
    @BindView(R.id.search_address_text)
    EditText address;

    private PlaceAdapter placeAdapter;
    private List<Place> places = new ArrayList<>();
    private LatLonPoint latLonPoint;
    private PoiSearch.Query query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LatLng latLng = getIntent().getParcelableExtra("point");
        latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        placeAdapter = new PlaceAdapter(this, places);
        recyclerView.setAdapter(placeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(this,
                recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                Place place = places.get(position);
                intent.putExtra("place", place);
                setResult(RESULT_OK, intent);
                finish();
            }
        }));
    }

    @OnClick(R.id.search_address_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.search_address_search)
    public void search() {
        if (TextUtils.isEmpty(address.getText().toString())) {
            ToastUtil.show(this, "请输入搜索关键字");
        } else {
            doSearchQueryWithKeyWord(address.getText().toString());
        }
    }

    protected void doSearchQueryWithKeyWord(String key) {
        int currentPage = 0;
        query = new PoiSearch.Query(key, "", "惠州市");
        query.setPageSize(20);
        query.setPageNum(currentPage);
        query.setCityLimit(true);
        if (latLonPoint != null) {
            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 5000, true));
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    List<PoiItem> poiItems = result.getPois();
                    places.clear();
                    Place place;
                    for (int i = 0; i < poiItems.size(); i++) {
                        PoiItem poiItem = poiItems.get(i);
                        if (i == 0) {
                            place = new Place(poiItem.getTitle(), poiItem.getSnippet(), true, poiItem.getLatLonPoint());
                        } else {
                            place = new Place(poiItem.getTitle(), poiItem.getSnippet(), false, poiItem.getLatLonPoint());
                        }
                        places.add(place);
                    }
                    placeAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtil.show(this, "对不起，没有搜索到相关数据！");
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
