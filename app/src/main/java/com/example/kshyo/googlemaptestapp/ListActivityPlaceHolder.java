package com.example.kshyo.googlemaptestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by kshyo on 2017-12-25.
 */

public class ListActivityPlaceHolder extends AppCompatActivity {
    EventData data = null;
    private static final LatLng tokyoTower = new LatLng(35.6584017, 139.7452774);
    private static final LatLng skyTree = new LatLng(34.7099277, 139.8105288);
    ArrayList arrayList = null;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        TextView textView;
        ArrayAdapter adapter;

        arrayList = new ArrayList();
        arrayList.add("TokyoTower");
        arrayList.add(tokyoTower);

        data = new EventData(arrayList);//
        Bundle bundle;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        intent = new Intent(this, MapsActivity.class);
        listView = (ListView) findViewById(R.id.container);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        // latlng 정보를 넣어갈 번들 생성
        bundle = new Bundle();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //리스트뷰 아이템에서 받아올 위도 경도
                LatLng latlng;
                //부모뷰에서 선택된 리스트 아이템뷰 오브젝트를 가져옴 이경우 arraylist
                ArrayList eventData = (ArrayList) parent.getItemAtPosition(position);
                // 이벤트 데이터에서 위치정보만 가져옴
                latlng = (LatLng) eventData.get(1);
                bundle.putParcelable("location", latlng);
                // 토스트로 위도 표시
                Toast.makeText(ListActivityPlaceHolder.this, String.valueOf(latlng), Toast.LENGTH_SHORT).show();

                // send latlng inf to MapsActivity and change activity
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }

    // 바로 블루맵으로 이동 == 자기 위치 에 따른 쿼리
    public void BluemapIntent(View view) {
        intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
