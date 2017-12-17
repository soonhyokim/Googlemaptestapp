package com.example.kshyo.googlemaptestapp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kshyo on 2017-12-17.
 */

public class MatchingDistrict {
    private HashMap<ArrayList<Double>, String> MyMap = new HashMap<>();
    int k = 0;
    ArrayList<Double> MyDistrictRange;
    String myDistrict;

    public MatchingDistrict(ArrayList<Double> mlist) {
        this.MyDistrictRange = mlist;
        setMyMap();

    }

    public void setMyMap() {
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
                MyMap.put(list, "D" + k);
            }
        }
    }

    public ArrayList<Double> getMyDistrictRange() {
        return this.MyDistrictRange;
    }

    public String getMyDistrict() {
        myDistrict = MyMap.get(getMyDistrictRange());
        return myDistrict;
    }
}