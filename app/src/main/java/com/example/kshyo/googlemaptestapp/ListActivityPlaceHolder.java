package com.example.kshyo.googlemaptestapp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView listView;
        TextView textView;
        ArrayAdapter adapter;


        data = new EventData("TokyoTower", tokyoTower);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);


        listView = (ListView) findViewById(R.id.container);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);
                Toast.makeText(ListActivityPlaceHolder.this, strText, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
