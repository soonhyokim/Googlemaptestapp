package com.example.kshyo.googlemaptestapp;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private String mName;

    public MyItem(double lat, double lng, String name) {
        mPosition = new LatLng(lat, lng);
        mName = name;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getName() {
        return mName;
    }
}