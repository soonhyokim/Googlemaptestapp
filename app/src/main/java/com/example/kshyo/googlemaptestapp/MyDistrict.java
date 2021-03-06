package com.example.kshyo.googlemaptestapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by kshyo on 2017-12-17.
 */

public class MyDistrict {


    private double mLat, mLng;
    private String MyDistrict;
    private ArrayList<Double> LntLngList = new ArrayList<Double>();
    private double minDistrictLat;
    private double minDistrictLng;
    private double maxDistrictLat;
    private double maxDistrictLng;
    String d1 = "D1";
    int i;

    public MyDistrict(double mLat, double mLng) {
        this.mLat = mLat;
        this.mLng = mLng;
        this.minDistrictLat = (double) (Math.round(mLat * 10.0 - 0.5)) / 10.0;
        this.maxDistrictLat = (double) (Math.round(mLat * 10.0 + 0.5)) / 10.0;
        this.minDistrictLng = (double) (Math.round(mLng * 10.0 - 0.5)) / 10.0;
        this.maxDistrictLng = (double) (Math.round(mLng * 10.0 + 0.5)) / 10.0;
    }


    public void setLntLngList() {
        LntLngList.add(this.minDistrictLat);
        LntLngList.add(this.maxDistrictLat);
        LntLngList.add(this.minDistrictLng);
        LntLngList.add(this.maxDistrictLng);
        Log.v("MyDistrict", "LntLngList : " + LntLngList);
    }

    public ArrayList<Double> getLntLngList() {
        return this.LntLngList;
    }


}

