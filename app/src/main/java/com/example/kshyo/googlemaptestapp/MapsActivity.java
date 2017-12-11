package com.example.kshyo.googlemaptestapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    // 마커 클릭시 지도 밑에 뜰 이벤트 정보 뷰
    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private TextView textView;


    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest request;
    private FusedLocationProviderApi api;
    private ClusterManager<MyItem> mClusterManager;
    private int CameraCount = 0;
    private int markerClickCounter = 0;

    // 지역 데이터, 나중엔 데이터 베이스에서 현재위치를 기반으로 쿼리해서 해당되는 위치 정보를 받아옴)
    private static final LatLng Aojora = new LatLng(35.4744471, 139.5798732);
    private static final LatLng Seintsu = new LatLng(35.4744366, 139.5803447);
    private static final LatLng BoundLeft = new LatLng(35.47, 139.58);
    private static final LatLng BoundRight = new LatLng(35.5, 139.60);
    //지도 줌아웃 제한 경계
    private LatLngBounds ADELAIDE = new LatLngBounds(
            new LatLng(35.47, 139.58), new LatLng(35.5, 139.60));

    //좌표 임시 저장
    private LatLng tempLatLng;
    //지도 위치 요청 코드>= 0
    private static final int MY_LOCATION_REQUEST_CODE = 1;

    private Marker mAojora;
    private Marker mSeintsu;
    private Marker mleft;
    private Marker mRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //이벤트 정보뷰의 레이아웃을 연결
        relativeLayout = (RelativeLayout) findViewById(R.id.info_view);
        textView = (TextView) findViewById(R.id.text_view);

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
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.48, 139.59), 13));

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


        // 지역1 위치
        mAojora = mMap.addMarker(new MarkerOptions()
                .position(Aojora)
                .title("aojora park"));
        mAojora.setTag(0);

        // 지역2 위치
        mSeintsu = mMap.addMarker(new MarkerOptions()
                .position(Seintsu)
                .title("seintsu house"));
        mSeintsu.setTag(1);
// 경계 지점 마커 감잡기용
        mleft = mMap.addMarker(new MarkerOptions()
                .position(BoundLeft)
                .title("left bound"));
        mSeintsu.setTag(1);

        mRight = mMap.addMarker(new MarkerOptions()
                .position(BoundRight)
                .title("right bound"));
        mSeintsu.setTag(1);


        // mMap.addMarker(new MarkerOptions()
        //        .position(current)
        //        .title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ADELAIDE.getCenter(), 12f));
        //마커 클러스터
        setUpClusterer();
        //지도 경계 설정
        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);

        //줌 인/아웃 제한
        mMap.setMinZoomPreference(12.0f);

        // 본인 위치로
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(this, "we need your permission to find event around you", Toast.LENGTH_SHORT).show();
            // Show rationale and request permission.
        }

        // Set a listener for marker click.
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this); // 리스너를 맨밑에 놓지 않으면 작동을 안함 ..
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


        if (CameraCount == 0) {// 카메라를 위치를 처음만 받음
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(current, 10f, 0, 0)));
        }

        //카메라 위치를 더이상 받지 않고자 카운트함
        CameraCount = 1;

        //animateCamera를 사용하면 현재위치를 카메라가 따라감
        //근데 moveCamera도 현재위치가 바뀌면 따라가게 되버림 미세한 오차가 발생할 경우 처리가 필요
        //터치리스너를 사용해서 터치한후에는 따라 가지 않도록 구현할 필요가 있어보임
        //나중에 정보뷰 인텐트로 넘어오는 경우와 이벤트 찾기 기능을 나눠서 if문으로 제어해줄 필요가 있어보임
    }

    /**
     * Called when the user clicks a marker.
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        //마커 클릭시 이벤트 정보뷰가 가시화됨
        relativeLayout.setVisibility(View.VISIBLE);
        textView.setText(marker.getTitle());

        //좌표를 임시적으로 저장
        tempLatLng = marker.getPosition();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    // 임의의 맵을 클릭 했을때 해당 좌표를 인수로 넘김
    @Override
    public void onMapClick(LatLng latLng) {
        Log.i("a", "mapclicked" + latLng);
        if (tempLatLng != latLng) relativeLayout.setVisibility(View.INVISIBLE);

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

