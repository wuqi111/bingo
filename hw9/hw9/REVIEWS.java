package com.example.wuqi.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class REVIEWS extends Fragment {

    private static final String TAG="REVIEWS";
    View view;
    private Button btntest;
    ArrayList<GoogleData> GoogleDatalist;

    String author_profile_url_google;
    String author_name_google;
    String rating_google;
    String time_google;
    String text_google;
    String review_request;
    String placeid_value;
    String yelpforaddress;
    String name_haha;
    String[] author_url= new String[20];
    GoogleData GoogleData=null;


    JSONArray yelp_data;

    String yelp_value_id;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerdetail, Bundle savedInstanceState) {



        view =inflater.inflate(R.layout.review,containerdetail,false);
         GoogleDatalist= new ArrayList<>();




        spinnerreview();

        btntest =(Button) view.findViewById(R.id.review);

        btntest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Toast.makeText(getActivity(),"testing button vlick 1",Toast.LENGTH_SHORT).show();
            }});
        Intent intent = getActivity().getIntent() ;
        review_request = intent.getStringExtra("review_request");

        placeid_value=intent.getStringExtra("placeidnumber");
        yelpforaddress=intent.getStringExtra("formatted_address");
         name_haha=intent.getStringExtra("name_pup");
        Log.d("djskfsd1234",yelpforaddress);

      // requestgooglerequest();
        requestyelprequest();



        return view;

    }


    private void requestgooglerequest() {


        try {
            JSONArray review_forrequest= new JSONArray(review_request);
            for (int i = 0; i <review_forrequest.length() ; i++) {
                JSONObject review_show= review_forrequest.getJSONObject(i);
                author_url[i]=review_show.getString("author_url");
                author_profile_url_google=review_show.getString("profile_photo_url");
                author_name_google=review_show.getString("author_name");

                rating_google=review_show.getString("rating");

//                RatingBar mRatingBar=(RatingBar) view.findViewById(R.id.rating_google);
//                mRatingBar.setRating(Float.parseFloat(rating_google));
                time_google=review_show.getString("time");
                text_google=review_show.getString("text");
                GoogleData=new GoogleData(author_profile_url_google,author_name_google,rating_google,time_google,text_google);
                GoogleDatalist.add(GoogleData);
                //Log.d("ddfjsls",text_google);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView listView= (ListView) view.findViewById(R.id.google_review_listview);
        GoogleDataListAdapter adapter= new GoogleDataListAdapter(getContext(),R.layout.google_review_layout,GoogleDatalist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("owijofmslf","sfjdn");
                Uri uri = Uri.parse( author_url[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    private void requestyelprequest() {

        String[] data_yelp= new String[0];
        String[] state_special=new String[0];
        data_yelp=yelpforaddress.split(",");
        int length_data=data_yelp.length;
        String state=data_yelp[length_data-2];
        String stress= data_yelp[length_data-3];
        state_special=state.split(" ");
        int length_state=state_special.length;

        String addressforyelp=data_yelp[0];
        String special_state=state_special[1];


        Log.d("sjdfjwoenfv", special_state);
        Log.d("sjdfjwodddenfv", stress);
        Log.d("djslsfergf",name_haha);



        String url_yelp_first="http://wuqi571-env.ignh24kvqj.us-east-2.elasticbeanstalk.com/gg?name="+name_haha+"&address="+addressforyelp+"&city="+stress+"&state="+special_state+"&country=US";
        RequestQueue queue= Volley.newRequestQueue(getContext().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_yelp_first, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray yelp_request=response.getJSONArray("businesses");
                            JSONObject yelp_id=yelp_request.getJSONObject(0);
                            yelp_value_id=yelp_id.getString("id");

                            requestyelpdatasecond();
                            Log.d("djsjwno",yelp_value_id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("dsjkljoiwjnf", String.valueOf(response));


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private void requestyelpdatasecond(){
       String  url_yelp_request="http://wuqi571-env.ignh24kvqj.us-east-2.elasticbeanstalk.com/kk?reivew="+yelp_value_id;

        RequestQueue queue=Volley.newRequestQueue(getContext().getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url_yelp_request, (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response.isNull("reviews")){
                                yelp_data=null;
                            }else{
                                yelp_data=response.getJSONArray("reviews");
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
//
//        ListView listView6= (ListView) view.findViewById(R.id.google_review_listview);
//        YelplistAdapter adapter= new YelplistAdapter(getContext(),R.layout.yelp_review_layout,yelpDatalist);
//        listView6.setAdapter(adapter);

        queue.add(jsonObjectRequest);
    }



    private void showyelpdata(){
        ArrayList<YelpData> YelpDatalist = new ArrayList<>();
        YelpData YelpData=null;
           // JSONArray yelpdata=yelp_data;

            if(yelp_data!=null){

                for (int i = 0; i <yelp_data.length() ; i++) {

                    try {
                        JSONObject  yelp_data_select = yelp_data.getJSONObject(i);
                        String yelp_text=yelp_data_select.getString("text");
                        String yelp_rating=yelp_data_select.getString("rating");
                        String yelp_time=yelp_data_select.getString("time_created");
                        JSONObject user= yelp_data_select.getJSONObject("user");
                        String yelp_image_url=user.getString("image_url");
                        String yelp_name=user.getString("name");
                        Log.d("jjsdjanfmsdj",yelp_name);
                        YelpData=new YelpData(yelp_image_url,yelp_name,yelp_rating,yelp_time,yelp_text);
                        YelpDatalist.add(YelpData);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ListView listView6= (ListView) view.findViewById(R.id.google_review_listview);
                YelplistAdapter adapter= new YelplistAdapter(getContext(),R.layout.yelp_review_layout,YelpDatalist);
                listView6.setAdapter(adapter);
            }
            else{

            }



        }

    private void spinnerreview() {

        Spinner spinner1 =(Spinner) view.findViewById(R.id.google_yelp_review);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(getContext(), R.array.select_review,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    requestgooglerequest();
                }else if(position==1){
//                    ListView listView=(ListView) view.findViewById(R.id.display_disapear);
//                    listView.setVisibility(view.INVISIBLE);
                    showyelpdata();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2 =(Spinner) view.findViewById(R.id.google_yelp_review_rating);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(getContext(), R.array.select_rating,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){

                 //   requestgooglerequest();
                }else if(position==1){

                    requestgooglerequest_rating_first();

                }else if(position==2){
                    requestgooglerequest_rating_opposite();

                }else if(position==3){
                    requestgoogletime_first();

                }else if(position==4){
                     requestgoogletime_opposite();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void  requestgooglerequest_rating_first(){

        Collections.sort(GoogleDatalist, new post_google_sort());


        ListView listView= (ListView) view.findViewById(R.id.google_review_listview);
        GoogleDataListAdapter adapter= new GoogleDataListAdapter(getContext(),R.layout.google_review_layout,GoogleDatalist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse( author_url[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
    private void requestgooglerequest_rating_opposite(){
        Collections.sort(GoogleDatalist, new post_google_sort_rating_opposite());


        ListView listView= (ListView) view.findViewById(R.id.google_review_listview);
        GoogleDataListAdapter adapter= new GoogleDataListAdapter(getContext(),R.layout.google_review_layout,GoogleDatalist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse( author_url[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private  void requestgoogletime_first(){
        Collections.sort(GoogleDatalist, new post_google_sort_time_first());


        ListView listView= (ListView) view.findViewById(R.id.google_review_listview);
        GoogleDataListAdapter adapter= new GoogleDataListAdapter(getContext(),R.layout.google_review_layout,GoogleDatalist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse( author_url[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void requestgoogletime_opposite(){
        Collections.sort(GoogleDatalist, new post_google_sort_time_opposite());


        ListView listView= (ListView) view.findViewById(R.id.google_review_listview);
        GoogleDataListAdapter adapter= new GoogleDataListAdapter(getContext(),R.layout.google_review_layout,GoogleDatalist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse( author_url[position]);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }


}



class post_google_sort implements Comparator<GoogleData> {

    @Override
    public int compare(GoogleData a, GoogleData b) {

        return  a.getRating_google().compareTo(b.getRating_google());
    }
}

class post_google_sort_rating_opposite implements Comparator<GoogleData> {

    @Override
    public int compare(GoogleData a, GoogleData b) {

        return  -a.getRating_google().compareTo(b.getRating_google());
    }
}
class post_google_sort_time_first implements Comparator<GoogleData> {

    @Override
    public int compare(GoogleData a, GoogleData b) {

        return  a.getTime_google().compareTo(b.getTime_google());
    }
}

class post_google_sort_time_opposite implements Comparator<GoogleData> {

    @Override
    public int compare(GoogleData a, GoogleData b) {

        return  -a.getTime_google().compareTo(b.getTime_google());
    }
}

