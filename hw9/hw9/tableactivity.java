package com.example.wuqi.hw9;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class tableactivity extends AppCompatActivity {
    String url_placeid;
    String next_page_value;
    String request_value_placeid;
    String url_next_page_first1;
    String nexticon;
    String nextname;
    String nextvicinity;
    String price_level ="";
    JSONArray photo_request=new JSONArray();
    JSONArray review_request=new JSONArray();
    String formatted_phone_number;
    String rating;
    String website;
    String url;
    String lat_use;
    String log_use;
    String formatted_address;

    ArrayList<Datasecond> NextDatalist = new ArrayList<>();
    Datasecond nextdata = null;

    private static final String TAG="Tab1fragment";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableactivity);



//        TextView outputText=(TextView) findViewById(R.id.data) ;
//        outputText.setText(getIntent().getStringExtra("accept_data"));

        Toolbar toolbar=(Toolbar) findViewById(R.id.second_toolbar);
         setSupportActionBar(toolbar);

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         toolbar.setNavigationOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){

                 finish();
             }
        });
        //add back button
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        showtablefirst();

        Button next=(Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog proDialog = android.app.ProgressDialog.show(tableactivity.this, "", "Fetching resutls！");
                Thread thread = new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            sleep(1000);
                        } catch (InterruptedException e)
                        {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                        proDialog.dismiss();//万万不可少这句，否则会程序会卡死。
                    }
                };
                thread.start();

                next_page_datashow();
//                show_next_page();
            }
        });


        Button previous =(Button) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showtablefirst();
            }
        });





    }

    private void showtablefirst() {
        int i=0;
        final String placeid[]= new String[20];
        ArrayList<Data> datalist = new ArrayList<>();
        Data data =null;
        ListView listView=(ListView) findViewById(R.id.procude_table);
        Intent intent = getIntent() ;
        lat_use=intent.getStringExtra("latitude");
        log_use=intent.getStringExtra("logitude");
        String result = intent.getStringExtra("accept_data") ;
        next_page_value=intent.getStringExtra("next_page_request");
        Log.d("wiodf",next_page_value);
        // JSONArray seet=JSONArray.fromOject(result);
        // String seek= result[0];
        try {
            JSONArray jsonArray = new JSONArray(result);

            for(i=0;i<jsonArray.length();i++){
                JSONObject seek=jsonArray.getJSONObject(i);
                String icon=seek.getString("icon");
                String name=seek.getString("name");
                String address=seek.getString("vicinity");
                data= new Data(icon, name, address);
                datalist.add(data);
            }

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject seek=jsonArray.getJSONObject(j);
                String place_id=seek.getString("place_id");
                placeid[j]=place_id;
            }

            Log.d("data1234324", String.valueOf(data));

            DatalistAdapter adapter= new DatalistAdapter(this,R.layout.layout_table,datalist);


            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    final ProgressDialog proDialog = android.app.ProgressDialog.show(tableactivity.this, "", "Fetching resutls！");
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

                    request_value_placeid=placeid[position];


                    request_information();

                }
            });


            //  Log.d("result", String.valueOf(seek));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void request_information() {


       // url_placeid="http://localhost:8081/f?place_id_value="+request_value_placeid;
       url_placeid="https://maps.googleapis.com/maps/api/place/details/json?placeid="+request_value_placeid+"&key=AIzaSyCLMrfYVBaXfPIa2AK6jZ0xCQdyBe9PF7w";
        Log.d("dsjiowem",url_placeid);
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_placeid, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONObject request_result=response.getJSONObject("result");
                            if(request_result.isNull("photos")){
                                photo_request=null;
                            }else{
                                photo_request=request_result.getJSONArray("photos");
                            }
                            //JSONArray photo_request=request_result.getJSONArray("photos");
                            if(request_result.isNull("reviews")){
                                review_request=null;
                            }else{
                                review_request=request_result.getJSONArray("reviews");
                            }

                            if(request_result.isNull("formatted_address")){
                                formatted_address="null";
                            }else{
                                formatted_address=request_result.getString("formatted_address");
                            }

                          //  String formatted_address=request_result.getString("formatted_address");

                            if(request_result.isNull("formatted_phone_number")){
                                formatted_phone_number="null";
                            }else{
                                formatted_phone_number=request_result.getString("formatted_phone_number");
                            }


                            if(request_result.isNull("price_level")){
                                price_level ="null";
                            }else{
                                price_level=request_result.getString("price_level");
                            }

                            if(request_result.isNull("rating")){
                                rating ="0";
                            }else{
                                rating=request_result.getString("rating");
                            }

                            if(request_result.isNull("url")){
                                url ="null";
                            }else{
                                url=request_result.getString("url");
                            }




                            if(request_result.isNull("website")){
                                website ="null";
                            }else{
                                website=request_result.getString("website");
                            }

                           // website=request_result.getString("website");


                            String namepup=request_result.getString("name");


                            Intent intent= new Intent(getApplicationContext(), detailActivity.class);

                            intent.putExtra("photo_request", String.valueOf(photo_request));
                            intent.putExtra("review_request", String.valueOf(review_request));
                            intent.putExtra("placeidnumber",request_value_placeid);
                            intent.putExtra("name_pup",namepup);
                            intent.putExtra("formatted_address", formatted_address);
                            intent.putExtra("formatted_phone_number", formatted_phone_number);
                            intent.putExtra("price_level", price_level);
                            intent.putExtra("rating", rating);
                            intent.putExtra("url",url);
                            intent.putExtra("website", website);
                            intent.putExtra("lat_map",lat_use);
                            intent.putExtra("log_map",log_use);

                            startActivity(intent);
//
//

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


    private  void next_page_datashow(){
//


        url_next_page_first1="http://wuqi571-env.ignh24kvqj.us-east-2.elasticbeanstalk.com/b?nextpage="+next_page_value;
        Log.d("jown",url_next_page_first1);
        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_next_page_first1, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray result=response.getJSONArray("results");
                            for (int i = 0; i < result.length(); i++) {
                                JSONObject seek1=result.getJSONObject(i);
                                nexticon=seek1.getString("icon");
                                nextname=seek1.getString("name");
                                nextvicinity=seek1.getString("vicinity");
                                nextdata =new Datasecond(nexticon,nextname,nextvicinity);
                                NextDatalist.add(nextdata);
                            }

               Log.d("sjoweij", String.valueOf(result));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //  accept_data.setText("Response: " + error.toString());
                    }
                });

        ListView listView7=(ListView) findViewById(R.id.procude_table);
        DatasecondlistAdapter adapter= new DatasecondlistAdapter(this,R.layout.layout_table,NextDatalist);
        listView7.setAdapter(adapter);


        queue.add(jsonObjectRequest);

    }

//   private void show_next_page(){
//
//   }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id=item.getItemId();
//        if(id==android.R.id.home){
//            this.finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
