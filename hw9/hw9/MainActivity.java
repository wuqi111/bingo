package com.example.wuqi.hw9;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG="MainActivity";

    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

//
//    private static final int GOOGLE_API_CLIENT_ID = 0;
//    private AutoCompleteTextView mAutocompleteTextView;
//    private TextView mNameView;
//
//    private GoogleApiClient mGoogleApiClient;
//    private PlaceArrayAdapter mPlaceArrayAdapter;
//
//    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
//            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
//
//


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id.other_palce);
//        mAutocompleteTextView.setThreshold(3);
//        mNameView = (TextView) findViewById(R.id.name);
//
//        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
//                .addApi(Places.GEO_DATA_API)
//                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
//                .addConnectionCallbacks(this)
//                .build();
//
//        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
//        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
//                BOUNDS_MOUNTAIN_VIEW, null);
//        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        mSectionPageAdapter=new SectionPageAdapter(getSupportFragmentManager());

        mViewPager=(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//
        tabLayout.getTabAt(0).setIcon(R.drawable.search);
        tabLayout.getTabAt(1).setIcon(R.drawable.heart_fill_white);











    }



    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter=new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1fragment(),"SEARCH");
        adapter.addFragment(new Tab2fragment(),"FAVORITE");

        viewPager.setAdapter(adapter);
    }


//    private AdapterView.OnItemClickListener mAutocompleteClickListener
//            = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
//            final String placeId = String.valueOf(item.placeId);
//            Log.i(TAG, "Selected: " + item.description);
//            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId);
//            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
//            Log.i(TAG, "Fetching details for ID: " + item.placeId);
//        }
//    };
//
//    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
//            = new ResultCallback<PlaceBuffer>() {
//        @Override
//        public void onResult(PlaceBuffer places) {
//            if (!places.getStatus().isSuccess()) {
//                Log.e(TAG, "Place query did not complete. Error: " +
//                        places.getStatus().toString());
//                return;
//            }
//// Selecting the first object buffer.
//            final Place place = places.get(0);
//            CharSequence attributions = places.getAttributions();
//
//            mNameView.setText(Html.fromHtml(place.getAddress() + ""));
//
//
//        }
//    };
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
//        Log.i(TAG, "Google Places API connected.");
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        mPlaceArrayAdapter.setGoogleApiClient(null);
//        Log.e(TAG, "Google Places API connection suspended.");
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//        Log.e(TAG, "Google Places API connection failed with error code: "
//                + connectionResult.getErrorCode());
//
//        Toast.makeText(this,
//                "Google Places API connection failed with error code:" +
//                        connectionResult.getErrorCode(),
//                Toast.LENGTH_LONG).show();
//
//    }

}
