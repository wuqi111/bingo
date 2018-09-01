package com.example.wuqi.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class INFO extends Fragment {

    String formatted_address;
    String formatted_phone_number;
    String price_level ;
    String rating;
    String url ;
    String website ;
    Float XX;

    private static final String TAG="INFO";

    private Button btntest;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containerdetail, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.info,containerdetail,false);
       // infodatarequest();


         Intent intent = getActivity().getIntent() ;
         formatted_address = intent.getStringExtra("formatted_address");
         formatted_phone_number = intent.getStringExtra("formatted_phone_number");



         price_level = intent.getStringExtra("price_level");

         if(price_level!=null){
           switch (price_level){
               case "1":
                   price_level="$";
                   break;
               case "2":
                   price_level="$$";
                   break;
               case "3":
                   price_level="$$$";
                   break;
               case "4":
                   price_level="$$$$";
                   break;
               case "5":
                   price_level="$$$$$";
                   break;
           }

         }

         rating = intent.getStringExtra("rating");
         url = intent.getStringExtra("url");
         website = intent.getStringExtra("website");
         Log.d("dfdsx",formatted_address);

         String formatted_address_name="Address";
        String formatted_phone_number_name="Phone Number";
        String price_level_name="Price Level";
        String rating_name="Rating";
        String url_name="Google Page";
        String website_name="Website";




        btntest =(Button) view.findViewById(R.id.info);
        btntest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Toast.makeText(getActivity(),"testing button vlick 1",Toast.LENGTH_SHORT).show();


            }});


        TextView Address=(TextView) view.findViewById(R.id.address_data);
        Address.setText(formatted_address);
        TextView Phone=(TextView) view.findViewById(R.id.phone_data);
        Phone.setText(formatted_phone_number);
        TextView Price=(TextView) view.findViewById(R.id.price_data);
        Price.setText(price_level);
        RatingBar Rating=(RatingBar) view.findViewById(R.id.rating_data);
       // simpleRatingBar.setRating((float) 3.5)


            XX=Float.parseFloat(rating);
            Rating.setRating(XX);


        TextView Google=(TextView) view.findViewById(R.id.google_data);

        TextView Website=(TextView) view.findViewById(R.id.website_data);
        Website.setText(website);


        TextView Address_key=(TextView) view.findViewById(R.id.address_key);
        Address_key.setText(formatted_address_name);
        TextView Phone_key=(TextView) view.findViewById(R.id.phone_key);
        Phone_key.setText(formatted_phone_number_name);
        TextView Price_key=(TextView) view.findViewById(R.id.price_key);
        Price_key.setText(price_level_name);
        TextView Rating_key=(TextView) view.findViewById(R.id.rating_key);
        Rating_key.setText(rating_name);
        TextView Google_key=(TextView) view.findViewById(R.id.google_key);
        Google_key.setText(url_name);
        TextView Website_key=(TextView) view.findViewById(R.id.website_key);
        Website_key.setText(website_name);


        String webLinkText = "<a href="+url+">"+url;
        Google.setText(Html.fromHtml(webLinkText));

        String dsf = "<a href="+website+">"+website;
        Website.setText(Html.fromHtml(dsf));

        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri11 = Uri.parse(url);
                            Intent intent111 = new Intent(Intent.ACTION_VIEW, uri11);
                            startActivity(intent111);
            }
        });

        Website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri11 = Uri.parse(website);
                            Intent intent111 = new Intent(Intent.ACTION_VIEW, uri11);
                            startActivity(intent111);
            }
        });



        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!formatted_phone_number.equals("null")) {
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(formatted_phone_number));
                    getContext().startActivity(intent);
                }

            }
        });




        //
//             ListView secondlistView=(ListView) view.findViewById(R.id.information);
//        String information_name[]={"Address","Phone Number","Price Level","Rating","Google Page","Website"};
//
//        ListView secondlistView=(ListView) view.findViewById(R.id.information_name);
//
//        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
//                getContext(),
//                R.layout.information_layout,
//                information_name
//        );
//        secondlistView.setAdapter(listViewAdapter);
//
//        String[] information_value= new String[]{formatted_address, formatted_phone_number, price_level, rating, url, website};
//        ListView secondlistView1=(ListView) view.findViewById(R.id.information_value);
//        ArrayAdapter<String> listViewAdapter1=new ArrayAdapter<String>(
//                getContext(),
//                R.layout.information_layout,
//                information_value
//        );
//        secondlistView1.setAdapter(listViewAdapter1);
//

         // build Information list
//                Information address1=new Information(formatted_address_name,formatted_address);
//                Information phone_number=new Information(formatted_phone_number_name,formatted_phone_number);
//                Information price_level1=new Information(price_level_name,price_level);
//                Information rating1=new Information(rating_name,rating);
//                Information google_page1=new Information(url_name,url);
//                Information website1=new Information(website_name,website);


       //  add information list to Informationlist
//                ArrayList<Information> informationlist=new ArrayList<>();
//                informationlist.add(address1);
//                informationlist.add(phone_number);
//                informationlist.add(price_level1);
//                informationlist.add(rating1);
//                informationlist.add(google_page1);
//                informationlist.add(website1);

//                Log.d("djsafdade", formatted_phone_number);



//                InformationlistAdapter adapter=new InformationlistAdapter(getActivity(), R.layout.information_layout,informationlist);
//                secondlistView.setAdapter(adapter);

//                secondlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if(position==4){
//                            Uri uri = Uri.parse(url);
//                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                            startActivity(intent);
//                        }
//
//                        if(position==5){
//                            Uri uri = Uri.parse(website);
//                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                            startActivity(intent);
//                        }
//
//                    }
//                });




        return view;
    }
//
//    private void infodatarequest() {
//
////        String information_name[]={"Address","Phone Number","Price Level","Rating","Google Page","Website"};
////        String information_value[]=new String[6];
//
//        Intent intent = getActivity().getIntent() ;
//        String formatted_address = intent.getStringExtra("formatted_address");
//        String formatted_phone_number = intent.getStringExtra("formatted_phone_number");
//        String price_level = intent.getStringExtra("price_level");
//        String rating = intent.getStringExtra("rating");
//        String url = intent.getStringExtra("url");
//        String website = intent.getStringExtra("website");
//
//        ListView listView=(ListView) getView().findViewById(R.id.information);
//                Information address=new Information("Address",formatted_address);
//                Information phone_number=new Information("Phone Number",formatted_phone_number);
//                Information price_level=new Information("Price Level",price_level);
//                Information rating=new Information("Rating",rating);
//                Information google_page=new Information("Google Page",url);
//                Information website=new Information("Website",website);
//
//
//
//
//
//        Log.d("placeid1232_value", formatted_address);
//
//    }


}
