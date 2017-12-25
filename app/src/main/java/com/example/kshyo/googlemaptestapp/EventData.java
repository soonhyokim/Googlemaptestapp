package com.example.kshyo.googlemaptestapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by kshyo on 2017-12-25.
 */

public class EventData {
    private LatLng latLng;
    private String name;
    private ArrayList arrayList;

    public ArrayList EventData(String mName, LatLng mLatLng) {
        arrayList.add(mName);
        arrayList.add(mLatLng);
        return arrayList;
    }


    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
