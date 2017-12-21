package com.example.kshyo.googlemaptestapp;

import java.util.ArrayList;


class MatchingDistrict {

    private int k = 0;
    private ArrayList<Double> MyDistrictRange;
    private String myDistrict;
    private ArrayList<String> myAllDistrict = new ArrayList<>();

    //생성자, 자신의 위도 경도 범위를 받아 저장함
    public MatchingDistrict(ArrayList<Double> mlist) {
        this.MyDistrictRange = mlist;
        if (MapsActivity.CameraCount == 0) setMyMap();

    }

    private void setMyMap() { // 데이터
        for (int i = 0; i < 199; i++) {// i 위도ㅇ
            for (int j = 0; j < 224; j++) { // j 경도
                k += 1; //카운트
                //일본 전 지역 위도 경도를 0.1 단위로 나눔
                ArrayList<Double> list = new ArrayList<>();
                list.add(24.4 + i * 0.1);
                list.add(24.5 + i * 0.1);
                list.add(123 + j * 0.1);
                list.add(123.1 + j * 0.1);
                //해쉬맵에 위도 경도 범위의 리스트를 키로 구역이름을 값으로 해서 넣음
                MapsActivity.MyMap.put(list, "D" + k);
            }
        }
    }//나중에 도쿄전용 해쉬맵, 오사카전용 해쉬맵을 나눠서 만들면 됨

    private ArrayList<Double> getMyDistrictRange() {
        return this.MyDistrictRange;
    }//

    private String getMyDistrict() {
        myDistrict = MapsActivity.MyMap.get(getMyDistrictRange());
        return myDistrict;
    }

    // 필요한 구역 이름을 리스트에 넣어서 반환
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