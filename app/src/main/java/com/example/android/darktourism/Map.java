package com.example.android.darktourism;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Map extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    Button searchBtn;
    EditText userLocation;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    double latitude, longitude;

    public Map() {
        //required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        userLocation = (EditText) view.findViewById(R.id.userInputLocation);
        mView = view;

        view.findViewById(R.id.userInputLocation).setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view,boolean hasFocus){
                if (hasFocus) {
                    userLocation.setHint("");
                }
                else {
                    userLocation.setHint("Enter Location");
                }
            }
        });

        view.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //if (view.getId() == R.id.searchBtn){
                //Toast.makeText(getActivity().getApplicationContext(), "pressed", Toast.LENGTH_LONG).show();
                String location = userLocation.getText().toString();
                List<Address> addressList = null;
                MarkerOptions markeroptions = new MarkerOptions();

                if (!location.equals("")){
                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext());
                    try {
                        addressList = geocoder.getFromLocationName(location, 5);
                        System.out.println("get input");
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }

                    for (int i = 0; i<addressList.size(); i++) {
                        Address myAddress = addressList.get(i);
                        LatLng latlng = new LatLng(myAddress.getLatitude(),myAddress.getLongitude());
                        markeroptions.position(latlng);
                        mGoogleMap.addMarker(markeroptions);
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                    }
                }
            }
            //}
        });

        return view;
    }

//        public void onClickSearch(View view){
//            //if (view.getId() == R.id.search_button){
//
//        }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        mGoogleMap.setOnMarkerDragListener(this);
        mGoogleMap.setOnMarkerClickListener(this);

    }

    protected synchronized void buildGoogleApiClient(){
        //following line might not work
        client = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
            //LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        }

    }

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if (currentLocationMarker != null){
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentLocationMarker = mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (client != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(client == null){
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

//    public static void regexChecker(String theRegex, String str2Check){
//        Pattern checkRegex = Pattern.compile(theRegex);
//        Matcher regexMatcher = checkRegex.matcher(str2Check);
//
//        while(regexMatcher.find()){
//            if(regexMatcher.group().length() != 0){
//                Log.e("searched", regexMatcher.group().trim());
//            }
//        }
//        return false;
//    }
}
