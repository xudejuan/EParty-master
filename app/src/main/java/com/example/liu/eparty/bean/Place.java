package com.example.liu.eparty.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.services.core.LatLonPoint;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/20:17:31
 * introduction：
 */


public class Place implements Parcelable {

    private String name;
    private String location;
    private boolean isChoose;
    private LatLonPoint latLonPoint;
    private String mainAddress;

    public Place(String name, String location, boolean isChoose, LatLonPoint latLonPoint) {
        this.name = name;
        this.location = location;
        this.isChoose = isChoose;
        this.latLonPoint = latLonPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.location);
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.latLonPoint, flags);
        dest.writeString(this.mainAddress);
    }

    protected Place(Parcel in) {
        this.name = in.readString();
        this.location = in.readString();
        this.isChoose = in.readByte() != 0;
        this.latLonPoint = in.readParcelable(LatLonPoint.class.getClassLoader());
        this.mainAddress = in.readString();
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
