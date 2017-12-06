package com.example.kshyo.googlemaptestapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest request;
    private FusedLocationProviderApi api;
    private ClusterManager<MyItem> mClusterManager;
    private int CameraCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //허가 확인 및 요구
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //위치정보의 요청정보를 취득
        request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(15);
        api = LocationServices.FusedLocationApi;

        // google play의 접속클라이언트를 생성
        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    //getter map
    public GoogleMap getMap() {
        return mMap;
    }

    private void setUpClusterer() {//나중에는 커스텀마커클러스터링으로 바꿔야함, 알고리즘을 커스터마이징해야함
        // Position the map.
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<>(this, getMap());

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 35.4744480;
        double lng = 139.5798730;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 기본 표시위치 설정
        LatLng current = new LatLng(37.6, 127);


        // mMap.addMarker(new MarkerOptions()
        //        .position(current)
        //        .title("You are here"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20f));
        setUpClusterer();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //위치정보가 변경될때 카메라 위치도 같이 변경
    @Override
    public void onLocationChanged(Location location) {
        if (mMap == null) {
            return;
        }
        LatLng current = new LatLng(
                location.getLatitude(), location.getLongitude());

        //지역1 ( 데이터 베이스에서 현재위치를 기반으로 쿼리해서 해당되는 위치 정보를 받아옴)
        LatLng location1 = new LatLng(35.4744471, 139.5798732);
        //지역2
        LatLng location2 = new LatLng(35.4744366, 139.5803447);

        // 지역1 위치
        mMap.addMarker(new MarkerOptions()
                .position(location1)
                .title("aojora park"));
        // 지역2 위치
        mMap.addMarker(new MarkerOptions()
                .position(location2)
                .title("seintsu house"));
        //현재위치 마커

        mMap.addMarker(new MarkerOptions().position(current).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("You are here!"));
        if (CameraCount == 0) {// 카메라를 위치를 처음만 받음
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(current, 10f, 0, 0)));
        }
        CameraCount = 1;
        //animateCamera를 사용하면 현재위치를 카메라가 따라감
        //근데 moveCamera도 현재위치가 바뀌면 따라가게 되버림 미세한 오차가 발생할 경우 처리가 필요
        //터치리스너를 사용해서 터치한후에는 따라 가지 않도록 구현할 필요가 있어보임
        //나중에 정보뷰 인텐트로 넘어오는 경우와 이벤트 찾기 기능을 나눠서 if문으로 제어해줄 필요가 있어보임
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // ACCESS_FINE_LOCATION의 허가를 확인
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        api.requestLocationUpdates(client, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (client != null) {
            client.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 위치정보 요구 해제 및 구글 플레이 접속 끊음
        if (client != null && client.isConnected()) {
            api.removeLocationUpdates(client, this);
        }
        client.disconnect();
    }

}

