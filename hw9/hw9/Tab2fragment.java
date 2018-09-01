package com.example.wuqi.hw9;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_WRITEABLE;

public class Tab2fragment extends Fragment {
    private static final String TAG="Tab2fragment";
    ArrayList<FavoriteData> favoriteData = new ArrayList<>();
    FavoriteData datalist1;
    private Button btntest;

    String icon_favorite;
    String name_favorite;
    String vicinity_favorite;

    @NonNull
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.tab2_fragment,container,false);

        btntest =(Button) view.findViewById(R.id.butTest2);

        btntest.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
              //  Toast.makeText(getActivity(),"testing button vlick 2",Toast.LENGTH_SHORT).show();


            }});

        final SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);
        icon_favorite=sharedPreferences.getString("ImageView","");
        name_favorite=sharedPreferences.getString("newname","");
        vicinity_favorite=sharedPreferences.getString("newaddress","");


        Log.d("ofsnldf",vicinity_favorite);

        datalist1= new  FavoriteData(icon_favorite,name_favorite,vicinity_favorite);
        favoriteData.add(datalist1);

        ListView listView=(ListView) view.findViewById(R.id.favorite);


        FavoritelistAdapter adapter= new FavoritelistAdapter(getContext(),R.layout.favorite_layout,favoriteData);


        listView.setAdapter(adapter);

//        showfavorite();

        return view;

    }


//

//    private void showfavorite() {
//
//    }

}
