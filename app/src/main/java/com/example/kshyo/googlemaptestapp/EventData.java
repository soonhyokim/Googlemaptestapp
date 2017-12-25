package com.example.kshyo.googlemaptestapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by kshyo on 2017-12-25.
 */

class EventData extends ArrayList{
    private LatLng latLng;
    private String name;

    public EventData(ArrayList list) {
        super.add(list);
        setName((String) list.get(0));
        setLatLng((LatLng) list.get(1));
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }
}
