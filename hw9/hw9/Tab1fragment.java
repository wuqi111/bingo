package com.example.wuqi.hw9;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab1fragment extends Fragment  implements AdapterView.OnItemSelectedListener,GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG="Tab1fragment";

    private Button btntest;

//autocomplete
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView mNameView;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
//


    // get data from activity_main.xml
    EditText receive_keyword, receive_distance,receive_otherlocation;
    Button search_first;
    TextView accept_data;

    //two button method
    RadioGroup rg;
    RadioButton rb;
    RadioButton tworb;
    //this is for receive category
     Spinner category;
    ArrayAdapter<CharSequence> adapter;

    //For ip_api request lat,log;
    String url="http://ip-api.com/json";


    //newurl for other place;
    String url_location;
    String url_table_first;
    String url_next_page_first;

    //here is for lat, long from ip_api
    String local_lat="",local_log="";

    String get_keywod="",get_otherlocation="",text="";
    String get_distance="";
    double real_number=0;
    String category_value;
    boolean status;

    String next_page_token;
    JSONArray test;

    //this is for permission for current location
    private int STORAGE_PERMISSION_CODE=1;

    @NonNull
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {



        final View view =inflater.inflate(R.layout.tab1_fragment,container,false);


        mAutocompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.other_palce);
        mAutocompleteTextView.setThreshold(3);
        mNameView = (TextView) view.findViewById(R.id.name);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(getContext(), android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);



        btntest =(Button) view.findViewById(R.id.butTest);

        btntest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Toast.makeText(getActivity(),"testing button vlick 1",Toast.LENGTH_SHORT).show();


            }});



        rg=(RadioGroup) view.findViewById(R.id.button_together) ;

        final Spinner spinner =(Spinner) view.findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(getContext(), R.array.more_category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        category_value="default";

        rb=(RadioButton) view.findViewById(R.id.local_place);
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // sendlocalipfunction();
            }
        });


        tworb=(RadioButton) view.findViewById(R.id.fore_place);
        tworb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               status=tworb.isChecked();
                //  sendrequestotherplace();
            }
        });

        //those variables from form
        accept_data=(TextView) view.findViewById(R.id.test);
        receive_keyword =(EditText) view.findViewById(R.id.keyword);
        receive_distance=(EditText) view.findViewById(R.id.distance);
     //   receive_otherlocation=(EditText) view.findViewById(R.id.other_palce);
        search_first=(Button) view.findViewById(R.id.search_first);



        search_first.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //realy pass data
                get_otherlocation=mAutocompleteTextView.getText().toString();
                Log.d("jafiown",get_otherlocation);
                get_keywod=receive_keyword.getText().toString();
                get_distance= receive_distance.getText().toString();
                category_value=spinner.getSelectedItem().toString();
                Log.d("category_value",category_value);


                if(get_keywod.length()==0){
                    TextView first_keyword=(TextView) view.findViewById(R.id.validation_First);
                    first_keyword.setVisibility(View.VISIBLE);
                }else if(get_keywod.length()!=0&& status==true&&get_otherlocation.length()==0){
                    TextView otherplace_validation=(TextView) view.findViewById(R.id.validation_second);
                    otherplace_validation.setVisibility(View.VISIBLE);
                }else if (get_keywod.length()==0 && status==true&&get_otherlocation.length()==0 ){
                    TextView first_keyword=(TextView) view.findViewById(R.id.validation_First);
                    first_keyword.setVisibility(View.VISIBLE);
                    TextView otherplace_validation=(TextView) view.findViewById(R.id.validation_second);
                    otherplace_validation.setVisibility(View.VISIBLE);
                }else {

                  final ProgressDialog proDialog = android.app.ProgressDialog.show(getContext(), "", "Fetching resutls！");
                  Thread thread = new Thread()
                  {
                      public void run()
                      {
                          try
                          {
                              sleep(1000);
                          } catch (InterruptedException e)
                          {

                              e.printStackTrace();
                          }
                          proDialog.dismiss();//万万不可少这句，否则会程序会卡死。
                      }
                  };
                  thread.start();


                  //HERE IS FOR PERMISSION
                  if(ContextCompat.checkSelfPermission(getContext(),
                          Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                      Toast.makeText(getContext(),"You have already granted this permission!", Toast.LENGTH_SHORT).show();
                  }else{
                      requestStoragePermission();
                  }

                  if(!get_otherlocation.equals("")) {
//                    //send request ip-api using volley
                      sendrequestotherplace();
//                    //send reqeust otherplace using volley
//
                  }else{
                      sendlocalipfunction();
                  }


                  if(get_distance.equals("")){
                      real_number=10*1609.34;
                  }else{
                      real_number= Integer.parseInt(get_distance)*1609;
                  }


                  requesttabledata();

                  //  opentableactivity();
              }




            }
        });



        Button clear=(Button) view.findViewById(R.id.clear_data);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receive_keyword.setText("");
                receive_distance.setText("");
                mAutocompleteTextView.setText("");
                rb.setChecked(true);
                spinner.setSelection(0);
                TextView first_keyword=(TextView) view.findViewById(R.id.validation_First);
                first_keyword.setVisibility(View.GONE);
                TextView otherplace_validation=(TextView) view.findViewById(R.id.validation_second);
                otherplace_validation.setVisibility(View.GONE);
            }
        });


        return view;
    }



    public void choose_one(View v){
        int radiobuttonid=rg.getCheckedRadioButtonId();
        rb=(RadioButton) v.findViewById(radiobuttonid);
    }




    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(getContext())
                    .setTitle("permission needed")
                    .setMessage("This permission is needed because of this and that ")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(getActivity(),new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            },STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION
            },STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Permission GRANTED",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void sendlocalipfunction() {
        RequestQueue queue= Volley.newRequestQueue(getContext().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            local_lat=response.getString("lat");
                            local_log=response.getString("lon");
                            Log.d("result",local_lat);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //  accept_data.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  accept_data.setText("Response: " + error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }




    private void requesttabledata(){

        url_table_first="http://wuqi571-env.ignh24kvqj.us-east-2.elasticbeanstalk.com/a?distance="+real_number+"&category="+category_value+"&keyword="+get_keywod+"&location1="+local_lat+"&location2="+local_log;
        Log.d("oiewnfl",url_table_first);
        RequestQueue queue=Volley.newRequestQueue(getContext().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_table_first, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                            next_page_token=response.getString("next_page_token");
                            test=response.getJSONArray("results");

                            opentableactivity();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  accept_data.setText("Response: " + error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private void sendrequestotherplace() {

        url_location="https://maps.googleapis.com/maps/api/geocode/json?address="+get_otherlocation+"&key=AIzaSyCLMrfYVBaXfPIa2AK6jZ0xCQdyBe9PF7w";

        RequestQueue queue=Volley.newRequestQueue(getContext().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_location, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result=response.getJSONArray("results");
                            JSONObject number=result.getJSONObject(0);
                            JSONObject geometry=number.getJSONObject("geometry");
                            JSONObject location=geometry.getJSONObject("location");
                            local_lat=location.getString("lat");
                            local_log=location.getString("lng");
//                            System.out.println(local_lat);
                            Log.d("check", local_lat);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        local_lat=response.getString();
//                        local_log=response.getString();
//                       Log.d("result",response.toString());
                        // accept_data.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  accept_data.setText("Response: " + error.toString());
                    }
                });

        queue.add(jsonObjectRequest);

    }


    private void opentableactivity(){

//        JSONArray accept_data=test;
        Log.d("accept", String.valueOf(test));
        Intent intent= new Intent(getActivity(), tableactivity.class);
        intent.putExtra("next_page_request",next_page_token);
        intent.putExtra("latitude",local_lat);
        intent.putExtra("logitude",local_log);
        Bundle bundle=new Bundle();
        bundle.putString("accept_data", String.valueOf(test));
        intent.putExtras(bundle);
        //intent.putExtra("accept_data", (Parcelable) accept_data);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text=parent.getItemAtPosition(position).toString();
        Log.d("check2",text);
//        String select_category = (String) parent.getSelectedItem();

//        Spinner check = (Spinner) view.findViewById(R.id.category);
//        String select_category = check.toString();
//        Log.d("check",select_category);
//        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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



}
