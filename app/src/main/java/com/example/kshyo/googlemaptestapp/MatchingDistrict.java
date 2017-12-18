package com.example.kshyo.googlemaptestapp;

import java.util.ArrayList;

/**
 * Created by kshyo on 2017-12-17.
 */

public class MatchingDistrict {

    int k = 0;
    ArrayList<Double> MyDistrictRange;
    String myDistrict;
    ArrayList<String> myAllDistrict = new ArrayList<>();

    public MatchingDistrict(ArrayList<Double> mlist) {
        this.MyDistrictRange = mlist;
        if (MapsActivity.CameraCount == 0) setMyMap();

    }

    public void setMyMap() { // 데이터
        for (int i = 0; i < 199; i++) {// i 위도ㅇ
            for (int j = 0; j < 224; j++) { // j 경도
                k += 1; //카운트
                //일본 전 지역 위도 경도를 0.1 단위로 나눔
                ArrayList<Double> list = new ArrayList<>();
                list.add(24.4 + i * 0.1);
                list.add(24.5 + i * 0.1);
                list.add(123 + j * 0.1);
                list.add(123.1 + j * 0.1);
                //해쉬맵에 넣음
                MapsActivity.MyMap.put(list, "D" + k);
            }
        }
    }

    public ArrayList<Double> getMyDistrictRange() {
        return this.MyDistrictRange;
    }

    public String getMyDistrict() {
        myDistrict = MapsActivity.MyMap.get(getMyDistrictRange());
        return myDistrict;
    }

    public ArrayList<String> getMyAllDistrict() {

        int len = getMyDistrict().length();
        int districtNumber = Integer.valueOf((String) getMyDistrict().subSequence(1, len)) - 226;
        for (int s = 0; s <= 2; s++) {
            for (int t = 1; t <= 3; t++) {
                myAllDistrict.add("D" + (districtNumber + 224 * s + t));
            }
        }
        return myAllDistrict;
    }
}