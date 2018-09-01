package com.example.wuqi.hw9;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MAP extends Fragment  implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final String TAG="MAP";
    private static final int overview = 0;


    String spinner_value_mode;
    Double lat_map;
    Double log_map;
    String address_endpoint;
    String location;
//    private static String FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
//    private static String CORSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;

    private Button btntest;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;


    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView mNameView;


    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerdetail, Bundle savedInstanceState) {

        View  view =inflater.inflate(R.layout.map,containerdetail,false);

        mAutocompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.address_first);
        mAutocompleteTextView.setThreshold(3);
        mNameView = (TextView) view.findViewById(R.id.name1);

        mAutocompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                address_endpoint=mAutocompleteTextView.getText().toString();


            }

            @Override
            public void afterTextChanged(Editable s) {
              //  address_endpoint=mAutocompleteTextView.getText().toString();

                DirectionsResult results = getDirectionsDetails(location,address_endpoint,TravelMode.DRIVING);
                if (results != null) {
                    mMap.clear();
                    setupGoogleMapScreenSettings(mMap);
                    addPolyline(results, mMap);
                    positionCamera(results.routes[overview], mMap);
                    addMarkersToMap(results, mMap);
                }
                Log.d("jowinfmld",address_endpoint);
            }
        });


        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        btntest =(Button) view.findViewById(R.id.map11);

        btntest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            }});

         //SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.show_mape);
        mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.show_mape);
       mapFragment.getMapAsync(this);




        final Spinner spinner =(Spinner) view.findViewById(R.id.mode_traveling);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(), R.array.modelfortravel,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
//        String mode_trabling =spinner.getSelectedItem().toString();
//        Log.d("wjfndjofd",mode_trabling);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value_mode=(String) spinner.getSelectedItem().toString();

                if(position!=0){
                    DirectionsResult results = getDirectionsDetails(location,address_endpoint,TravelMode.valueOf(spinner_value_mode));
                    if (results != null) {
                         mMap.clear();
                        setupGoogleMapScreenSettings(mMap);
                        addPolyline(results, mMap);
                        positionCamera(results.routes[overview], mMap);
                        addMarkersToMap(results, mMap);
                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent =getActivity().getIntent();
        location=intent.getStringExtra("formatted_address");
        Log.d("wueoijnd",location);
        lat_map=Double.parseDouble(intent.getStringExtra("lat_map"));
        log_map=Double.parseDouble(intent.getStringExtra("log_map"));


//        AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView) view.findViewById(R.id.address_first);
//        address_endpoint=autoCompleteTextView.getText().toString();
//
//        Log.d("owfnjdfswerf",address_endpoint);
//
//
//            if(isSersiesOK()){
//                init();
//            }


        return view;

    }

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode){

        try {
            return DirectionsApi.newRequest(getGeoContext())
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        Log.d("oeijfld","owen");
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng hcmus = new LatLng(lat_map,log_map);

        mMap.addMarker(new MarkerOptions().position(hcmus).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus,18));





    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setTrafficEnabled(true);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }


    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.lat,results.routes[overview].legs[overview].startLocation.lng)).title(results.routes[overview].legs[overview].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat,results.routes[overview].legs[overview].endLocation.lng)).title(results.routes[overview].legs[overview].startAddress).snippet(getEndLocationTitle(results)));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private String getEndLocationTitle(DirectionsResult results){
        return  "Time :"+ results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }


    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }
    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.google_maps_API_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }




    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
// Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            mNameView.setText(Html.fromHtml(place.getAddress() + ""));


        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());



    }


//    private void getLovationPermission(){
//        String[] permission ={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}
//
//        if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//            if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(),CORSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//                mLocationPermissionsgranted=true;
//            }
//            else{
//                ActivityCompat.requestPermissions(getActivity(),permission,1234);
//            }
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            mLocationPermissionsgranted=false;
//        switch (requestCode){
//            case
//        }
//    }
}
