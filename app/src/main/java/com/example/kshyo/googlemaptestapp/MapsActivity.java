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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest request;
    private FusedLocationProviderApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //허가 확인 및 요구
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //위치정보의 요청정보를 취득
        request =LocationRequest.create()
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
        mMap.addMarker(new MarkerOptions().position(current).alpha(0.5f).rotation(4f).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20f));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //위치정보가 변경될때 카메라 위치도 같이 변경
    @Override
    public void onLocationChanged(Location location) {
if(mMap == null){return;}
        LatLng current = new LatLng(
                location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("You are here!"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16f));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // ACCESS_FINE_LOCATION의 허가를 확인
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        api.requestLocationUpdates(client,request,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(client != null){
            client.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 위치정보 요구 해제 및 구글 플레이 접속 끊음
        if (client != null && client.isConnected()){
            api.removeLocationUpdates(client, this);
        }
        client.disconnect();
    }

}
