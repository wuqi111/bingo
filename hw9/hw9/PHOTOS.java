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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.utils.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PHOTOS extends Fragment
{
    private static final String TAG="PHOTOS";

    private Button btntest;


    //here is for img loader
    private ListView imgview;
    private imageListAdapter mImageListAdapter;
    private List<String> mList;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerdetail, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.photos,containerdetail,false);

       // request_photos();
        imgview=(ListView)view.findViewById(R.id.photo_photo);
        mList=new ArrayList<>();

        btntest =(Button) view.findViewById(R.id.photos);

        btntest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Toast.makeText(getActivity(),"testing button vlick 1",Toast.LENGTH_SHORT).show();
            }});

        String[] photo_forrequest=new String[10];
        String[] url_photorequest=new String[10];
        Intent intent = getActivity().getIntent() ;
        String photo_request = intent.getStringExtra("photo_request");
        Log.d("djskfsd",photo_request);
        try {
            JSONArray request_photo=new JSONArray(photo_request);
            for (int i = 0; i < request_photo.length(); i++) {
                JSONObject photo_show=request_photo.getJSONObject(i);
                photo_forrequest[i]=photo_show.getString("photo_reference");
                url_photorequest[i]="https://maps.googleapis.com/maps/api/place/photo?maxwidth=750&photoreference="+photo_forrequest[i]+"&key=AIzaSyCySUo-85EpYeYIv_cgnZoBeppOUve67tc";
                mList.add(url_photorequest[i]);
            }

            Log.d("reuqestforphoto", String.valueOf(request_photo));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mImageListAdapter=new imageListAdapter(getActivity(),mList);
        imgview.setAdapter(mImageListAdapter);

        imgview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String text=parent.getItemAtPosition(position).toString();
                Uri uri = Uri.parse(text);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Log.d("placeid2342_value", photo_request);


        return view;

    }



}
