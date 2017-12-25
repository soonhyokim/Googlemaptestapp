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
    private static final LatLng tokyoTower = new LatLng(35.6587110, 139.7459622);
    private static final LatLng skyTree = new LatLng(34.7099277, 139.8105288);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        TextView textView;
        ArrayAdapter adapter;


        data = new EventData("TokyoTower", tokyoTower);//
        data = new EventData("SkyTree", skyTree);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        Intent intent = new Intent(this , MapsActivity.class);
        listView = (ListView) findViewById(R.id.container);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);
                LatLng latlng  = (LatLng) parent.getItemAtPosition(position);

                Toast.makeText(ListActivityPlaceHolder.this, strText, Toast.LENGTH_SHORT).show();
                Toast.makeText(ListActivityPlaceHolder.this, String.valueOf(latlng), Toast.LENGTH_SHORT).show();
                // send latlng inf to MapsActivity and change activity
                intent.putExtra(strText, latlng);
                startActivity(intent);
            }
        });
    }
}
