package com.example.kshyo.googlemaptestapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by kshyo on 2017-12-25.
 */

class EventData extends ArrayList{
    private LatLng latLng;
    private String name;

    public  EventData(String mName, LatLng mLatLng) {
        this.latLng = mLatLng;
        this.name = mName;
        super.add(mName);
        super.add(mLatLng);
    }


    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
